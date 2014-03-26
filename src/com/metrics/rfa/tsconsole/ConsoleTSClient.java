package com.metrics.rfa.tsconsole;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.FieldListDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dao.TsDao;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.FieldList;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.dbo.Ts;
import com.metrics.MDOL.util.TimeUtil;
import com.metrics.rfa.framework.idn.RefChainTimeSeriesDefDb;
import com.metrics.rfa.framework.idn.TS1TimeSeries;
import com.metrics.rfa.framework.idn.TS1TimeSeriesClient;
import com.reuters.ts1.TS1Def;
import com.reuters.ts1.TS1Event;
import com.reuters.ts1.TS1Point;
import com.reuters.ts1.TS1Sample;
import com.reuters.ts1.TS1Series;

/**
 * The TS console client
 */
public class ConsoleTSClient implements TS1TimeSeriesClient {
    protected ConsoleTSClient(RefChainTimeSeriesDefDb tsdb, TS1TimeSeries ts, int count, ApplicationContext context) {
        // _timeSeries = ts;
        ts.setClient(this);
        _tsdb = tsdb;
        _count = count;
        _context = context;
        //System.out.println("Subscribing to " + ts.series().getBaseName());
    }

    
    public void processTimeSeriesComplete(TS1TimeSeries series) {
        dumpTimeSeries(series.series());
    }

    public void processTimeSeriesError(TS1TimeSeries series) {
        System.out.println("series could not be processed: " + series.text());
    }

	private void dumpTimeSeries(TS1Series series) {
    	TsDao tsDao = (TsDao) _context.getBean("tsDaoProxy");
    	FieldDao fieldDao = (FieldDao) _context.getBean("fieldDaoProxy");
    	SymbolDao symbolDao = (SymbolDao) _context.getBean("symbolDaoProxy");
    	FieldListDao fieldListDao = (FieldListDao) _context.getBean("fieldListDaoProxy");
    	List<Ts> updateList = new ArrayList<Ts>();
    	List<Ts> saveList = new ArrayList<Ts>();
    	
    	String item = series.getBaseName();
    	item = "["+item+"]";
    	
        DateFormat formatter = new SimpleDateFormat("yyyy MMM dd   HH:mm");

        String[] months = {"1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" , "11" , "12"};
        		
        ArrayList<TS1Event> eventList = series.getEventList();
        if (eventList != null)
        {
            if (eventList.iterator().hasNext())
                System.out.println("Events");
            for (Iterator<TS1Event> iter = eventList.iterator(); iter.hasNext();)
            {
                TS1Event event = (TS1Event)iter.next();
                System.out.print(formatter.format(event.getDate().getTime()));
                System.out.print('\t');
                System.out.println(event.toString());
            }
        }

        
        System.out.println("Processing "+item+"...");
        
        for (int i = 0; i < series.getFactCount(); i++) {
            int fid = series.getFact(i);
            TS1Def def = _tsdb.defDb().getDef(fid);
        	if(def != null){
        		if(def.getLongName() != null && !"".equals(def.getLongName())){
        			String defName = def.getLongName();
        			defName = defName.replace(" ", "_");
        			if(defName.equals("VOLUME")){
        				defName = "ACVOL_1";
        			} else if(defName.equals("HIGH")){
        				defName = "HIGH_1";
        			} else if(defName.equals("LOW")){
        				defName = "LOW_1";
        			} else if(defName.equals("OPEN")){
        				defName = "OPEN_PRC";
        			} else if(defName.equals("CLOSE")){
        				defName = "HST_CLOSE";
        			} else if(defName.equals("LAST_TRADE_PRICE")){
        				defName = "TRDPRC_1";
        			}
                	fidList.add(defName);
        		}
        	}
        }
        Iterator<TS1Sample> sampleIter = series.sampleIterator();
        
        int k = 0;
        
		while(sampleIter.hasNext() && (k < _count)){
    		TS1Sample sample = sampleIter.next();
            Calendar date = sample.getDate();
            
            String time = date.get(Calendar.YEAR)+"-"+months[date.get(Calendar.MONTH)]+"-"+date.get(Calendar.DAY_OF_MONTH);
            
	    	Symbol symbol = symbolDao.getByName(series.getBaseName());
	    	
    		Ts timeSeries = new Ts();
            timeSeries.setTs(symbol.getName());
            Timestamp timeConvert = TimeUtil.getTimeStamp(TimeUtil.getDate(time));
            timeSeries.setDate(timeConvert);
            
            List<FieldList> list = fieldListDao.getAllList();
            TS1Point[] points = sample.getPoints();

            for(int i=0 ; i<fidList.size() ; i++){
                Field field = null;
            	String fidName = fidList.get(i);
            	for(FieldList fl : list){
            		if(fl.getName().equals(fidName)){
            			field = fieldDao.getByFieldAndSymbol(fl.getName(), symbol);
            			break;
            		}
            	}
            	if(field != null){
            		String val = points[i].toString().replace(",", "");
            		if(field.getName().equals("BID")){
            			timeSeries.setBid(val);
            		} else if(field.getName().equals("ASK")){
            			timeSeries.setAsk(val);
            		} else if(field.getName().equals("ACVOL_1")){
            			timeSeries.setVol(val);
            		} else if(field.getName().equals("HIGH_1")){
            			timeSeries.setHigh(val);
            		} else if(field.getName().equals("LOW_1")){
            			timeSeries.setLow(val);
            		} else if(field.getName().equals("OPEN_PRC")){
            			timeSeries.setOpen(val);
            		} else if(field.getName().equals("HST_CLOSE")){
            			timeSeries.setClose(val);
            		} else if(field.getName().equals("TRDPRC_1")){
            			timeSeries.setLast(val);
            		}
            	}
            }
        	Ts tsChk = tsDao.getByNameAndDate(series.getBaseName(), timeConvert);
        	if(tsChk != null){
        		//The data is exist
        		String tsTime = tsChk.getDate().toString();
        		tsTime = tsTime.substring(0,10);
        		String now = TimeUtil.getTimeStamp(new Date()).toString();
        		now = now.substring(0,10);
        		if(now.equals(tsTime)){
        			//tsDao.update(tsChk);
        			updateList.add(tsChk);
        		}
        	} else {
            	//tsDao.save(timeSeries);
            	saveList.add(timeSeries);
        	}
            k++;
		}
		tsDao.updateAll(updateList);
        tsDao.saveAll(saveList);
        System.out.println("Total count: " + k);
    }

    private RefChainTimeSeriesDefDb _tsdb;
    private int _count;
    private ApplicationContext _context;
    private List<String> fidList = new ArrayList<String>();
    
}
