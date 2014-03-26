package com.metrics.MDOL.snapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.bean.NowLast;
import com.metrics.MDOL.dao.ExchangeDao;
import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.QuoteDao;
import com.metrics.MDOL.dbo.Exchange;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Quote;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.util.Streamer;
import com.metrics.MDOL.util.TimeUtil;

public class Runner {
	
	private ApplicationContext _context;
	private QuoteDao quoteDao;
	private FieldDao fieldDao;
	private ExchangeDao exchangeDao;
	
	List<Field> fieldAll = new ArrayList<Field>();
	List<Exchange> exchAll = new ArrayList<Exchange>();
	
	public Runner(ApplicationContext context){
		_context = context;
		quoteDao = (QuoteDao) _context.getBean("quoteDaoProxy");
		fieldDao = (FieldDao) _context.getBean("fieldDaoProxy");
		exchangeDao = (ExchangeDao) _context.getBean("exchangeDaoProxy");

		
	}
	
	public void runner() {
		fieldAll = fieldDao.getAll();
		exchAll = exchangeDao.getAll();

		Timestamp time = TimeUtil.getTimeStamp(new Date());
		
		List<Quote> quoteList = new ArrayList<Quote>();

		
		
		for(int i=0 ; i<fieldAll.size() ; i++){
			Symbol symbol = fieldAll.get(i).getSymbol();
			Field field = fieldAll.get(i);
			String symName = "["+symbol.getName()+"]";
			String expression = fieldAll.get(i).getExpression();
			
			int skip = 0;
			if(symName.contains(".")){
				String rdn = Streamer.map.get(symName+"RDN_EXCHID").get(0).getVal();
				Exchange exc = null;
				for(Exchange e : exchAll){
					String eName = e.getValue();
					if(rdn.equals(eName)){
						exc = e;
						break;
					}
				}
				
				
				String open1 = "";
				String open2 = "";
				String close1 = "";
				String close2 = "";
				String date = TimeUtil.getDateString(new Date());
				Timestamp now = TimeUtil.getTimeStamp(new Date());
				long nowL = TimeUtil.getDateTimeInMillis(now);
				
				if(exc != null){
					open1 = exc.getFirstOpen();
					open2 = exc.getSecondOpen();
					close1 = exc.getFirstClose();
					close2 = exc.getSecondClose();
					
					long open1L = TimeUtil.getDateTimeInMillis(TimeUtil.getTimeStamp(date+" "+open1));
					long close1L = TimeUtil.getDateTimeInMillis(TimeUtil.getTimeStamp(date+" "+close1));
					
					if(open2 != null && !"".equals(open2)){
						long open2L = TimeUtil.getDateTimeInMillis(TimeUtil.getTimeStamp(date+" "+open2));
						long close2L = TimeUtil.getDateTimeInMillis(TimeUtil.getTimeStamp(date+" "+close2));
						
						if((nowL > open1L && nowL < close1L) || (nowL > open2L && nowL < close2L)){
							skip = 0;
						} else {
							skip = 1;
						}
					} else {
						if((nowL > open1L && nowL < close1L)){
							skip = 0;
						} else {
							skip = 1;
						}
					}
				}
			} 
			
			if(skip == 1){
//				System.err.println(symbol.getName()+" : Closed");
			} else {
				Quote quote = new Quote();
				String displayName = "-";
				List<NowLast> disp = Streamer.map.get(symName+"DSPLY_NAME");
				if(disp != null){
					displayName = disp.get(0).getVal();
				}
				
				String value = "";
				List<NowLast> list = Streamer.map.get(expression);
				if(list != null){
					value = list.get(0).getVal();
				}
				
				quote.setField(field);
				quote.setSymbol(symbol);
				if(value.equals("")){
					value = "-1";
				}
				quote.setValNow(Double.valueOf(value));
				quote.setInsertDate(time);
				quote.setValLast(Double.valueOf(value));
		    	quote.setDisplayName(displayName);
		    	
		    	String serverTime = "";
		    	String serverDate = "";
		    	
		    	List<NowLast> dateList = Streamer.map.get(symName+"VALUE_DT1");
		    	List<NowLast> timeList = Streamer.map.get(symName+"VALUE_TS1");
		    	
		    	if(dateList != null && timeList != null){
		    		serverTime = timeList.get(0).getVal();
		    		serverDate = dateList.get(0).getVal();
		    	}

				if(!value.equals("-1") && !field.getExpression().equals("-1")){
					Timestamp serverUpdateTimeStamp = null;
					if(serverTime != null && serverDate != null && !serverTime.equals("") && !serverDate.equals("") ){
				    	String serverUpdate = serverDate + serverTime;
				    	if(serverUpdate != null && !"".equals(serverUpdate)){
					    	serverUpdateTimeStamp = TimeUtil.getTimeOMM(serverUpdate);
					    	quote.setServerTime(serverUpdateTimeStamp);
				    	}
					} else {
						/*Timestamp t = quoteDao.getTopServerDateData(symbol);
				    	quote.setServerTime(t);*/
						quote.setServerTime(time);
					}

					quoteList.add(quote);
				} else {
//					System.err.println(symbol.getName()+" : "+field.getName()+" no value");
				}
			}

		}
		quoteDao.saveAll(quoteList);
		System.out.println("Runner : COMPLETE at "+TimeUtil.getTimeString(new Date()));
	}

}
