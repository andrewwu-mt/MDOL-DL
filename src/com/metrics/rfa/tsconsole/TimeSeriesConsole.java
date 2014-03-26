package com.metrics.rfa.tsconsole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dao.TsDao;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.dbo.Option;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.dbo.Ts;
import com.metrics.rfa.framework.idn.RefChainTimeSeriesDefDb;
import com.metrics.rfa.framework.idn.TS1TimeSeries;
import com.metrics.rfa.framework.sub.SubAppContext;
import com.metrics.rfa.framework.sub.SubAppContextClient;
import com.metrics.rfa.utility.CommandLine;
import com.reuters.ts1.TS1Constants;

/**
 * The TS console application
 */

public class TimeSeriesConsole implements SubAppContextClient
{
    // Configuration
    protected String _serviceName;
    protected LinkedList<String> _itemNames;
    protected SubAppContext _appContext;
    protected RefChainTimeSeriesDefDb _tsdb;
    private String symbolsOri = "";
    private String fieldOri = "";
    
    public static Map<String, List<Ts>> map = new LinkedHashMap<String, List<Ts>>();

    
	ApplicationContext context;	
	SymbolDao symbolDao;
	FieldDao fieldDao;
	ItemDao itemDao;
	OptionDao optionDao;

    
    public TimeSeriesConsole( ApplicationContext _context)
    {
    	context = _context;
    	symbolDao = (SymbolDao) context.getBean("symbolDaoProxy");
    	fieldDao = (FieldDao) context.getBean("fieldDaoProxy");
    	itemDao = (ItemDao) context.getBean("itemDaoProxy");
    	optionDao = (OptionDao) context.getBean("optionDaoProxy");
    	
    	SubAppContext.addCommandLineOptions();

    	Option option = optionDao.getOptionBy(1);
        _serviceName = option.getServiceName();
        String session = option.getSessionName();
        String enumType = option.getEnumType();
        
        CommandLine.addOption("serviceName", _serviceName, "service to request");
        CommandLine.addOption("session", session, "Session name to use");
        CommandLine.addOption("enumType", enumType, "enumtype.def filename");
        CommandLine.addOption("rdmFieldDictionary", "RDMFieldDictionary", "RDMFieldDictionary filename");
        String[] argv = {};
        CommandLine.setArguments(argv);
        _appContext = SubAppContext.createOMM(System.out);
        _appContext.setAutoDictionaryDownload();
        _appContext.setCompletionClient(this);
        run();
    }

    public void processComplete()
    {
    	
    	Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				List<Field> fieldFirst = fieldDao.getAll();
	            StringBuilder fieldOris = new StringBuilder();
		    	
		    	for(int i=0 ; i<fieldFirst.size() ; i++){
		    		if(fieldOris.length() > 0){
		    			fieldOris.append(", ");
				    }
		    		fieldOris.append(fieldFirst.get(i).getUpdateDate().toString());
		    	}
		    	
		    	fieldOri = fieldOris.toString();
		    	
				while(true){
			    	List<Item> activeItems = itemDao.getAllActiveItem();
			    	List<String> activeStrings = new ArrayList<String>();
			    	
			    	for(int i=0 ; i<activeItems.size() ; i++){
			    		String name = activeItems.get(i).getName();
			    		
			    		String nameEdited = "";
			    		if(name.contains("=")){
			    			nameEdited = name.substring(name.lastIndexOf("=")).trim();
			    			name = name.replace(nameEdited , "");
			    		}
			    		
			    		if(!activeStrings.contains(name)){
			    			activeStrings.add(name);
			    		}
			    	}
			    	
			    	
			    	
			    	List<Symbol> symbols = symbolDao.getAllActiveSymbol();
			    	
			    	List<String> itemList = new ArrayList<String>();
			    	
			    	for(int i=0 ; i<symbols.size() ; i++){
			    		for(int j=0 ; j<activeStrings.size() ; j++){
			    			String activeString = activeStrings.get(j);
			    			String activeSymbol = symbols.get(i).getName();
			    			
			    			
			    			if(activeSymbol.contains(activeString)){
			    				String item = "";
			    				if(!activeString.contains(".")){
			    					item = activeString + "=";
			    				} else {
			    					item = activeString;
			    				}
				    			itemList.add(item);
				    			break;
			    			} 
			    		}
			    	}
			    	StringBuilder itemNow = new StringBuilder();
			    	
			    	for(int i=0 ; i<itemList.size() ; i++){
			    		if(itemNow.length() > 0){
			    			itemNow.append(", ");
					    }
			    		itemNow.append(itemList.get(i));
			    	}
			    	
			    	String symbolsNow = itemNow.toString().replace(" ", "");//current symbols
			    	
			    	TsDao tsDao = (TsDao) context.getBean("tsDaoProxy");
			    	
	                
			    	_tsdb = new RefChainTimeSeriesDefDb(_appContext);
		    		
		    		StringTokenizer st = new StringTokenizer(symbolsNow, ",");
		            _itemNames = new LinkedList<String>();
		            while (st.hasMoreTokens())
		                _itemNames.add(st.nextToken().trim());
		            
		            Iterator<String> iter = _itemNames.iterator();
		            
		            
		            //Compare field list
		            List<Field> fields = fieldDao.getAll();
		            StringBuilder fieldNow = new StringBuilder();
			    	
			    	for(int i=0 ; i<fields.size() ; i++){
			    		if(fieldNow.length() > 0){
			    			fieldNow.append(", ");
					    }
			    		fieldNow.append(fields.get(i).getUpdateDate().toString());
			    	}
			    	
			    	//String fieldsNow = fieldNow.toString().replace(" ", "");//current fields
			    	String fieldsNow = fieldNow.toString();//current fields

		    		List<String> itemNames = new ArrayList<String>();

			    	if(!fieldsNow.equals(fieldOri)){
			    		String[] fieldString = fieldOri.split(",");
			    		
			    		for(int i=0 ; i<fields.size() ; i++){
			    			String update = fields.get(i).getUpdateDate().toString();
			    			update = update.replace(" ", "");
			    			String ori = "";
			    			if(i>fieldString.length - 1){
			    				Field field = fields.get(i);
			    				if(!itemNames.contains(field.getSymbol().getName())){
			    					itemNames.add(field.getSymbol().getName());
			    				}
			    			} else {
			    				ori = fieldString[i].replace(" ", "");
			    			}
			    			if(!update.equals(ori)){
			    				Field field = fields.get(i);
			    				if(!itemNames.contains(field.getSymbol().getName())){
			    					itemNames.add(field.getSymbol().getName());
			    				}
			    			}
			    		}
			    		fieldOri = fieldsNow;
			    	}
			    	
			    	
			    	if(!symbolsNow.equals(symbolsOri)){
			    		symbolsOri = symbolsNow;
			    		
			            while (iter.hasNext())
			            {
			                String itemName = (String)iter.next();
			                
			                Ts ts = tsDao.getByName(itemName);
			                
			                int count = 0;
			                if(ts != null){
			                	//count = Integer.MAX_VALUE;
			                	count = 10;
			                } else {
			                	count = Integer.MAX_VALUE;
			                }
			                
			                TS1TimeSeries timeseries = new TS1TimeSeries(_appContext, itemName, TS1Constants.DAILY_PERIOD, count);
			                new ConsoleTSClient(_tsdb, timeseries, count, context);
			            }
			    	} else {
			    		while (iter.hasNext())
			            {
			                String itemName = (String)iter.next();
			                
			                int count = 10;
			                
			                TS1TimeSeries timeseries = new TS1TimeSeries(_appContext, itemName, TS1Constants.DAILY_PERIOD, count);
			                new ConsoleTSClient(_tsdb, timeseries, count, context);
			            }
			            System.out.println("TSConsole : Nothing changed");
					}
			    	
			    	try {
						Thread.sleep(86400000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
        });
    	t.start();
    	
        
    }

    public void run()
    {
        _appContext.run();
    }

    public void cleanup()
    {
        _appContext.cleanup();
    }

}
