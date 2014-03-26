package com.metrics.MDOL.snapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.context.ApplicationContext;

import com.metrics.MDOL.bean.NowLast;
import com.metrics.MDOL.dao.ExchangeDao;
import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.FieldListDao;
import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dao.QuoteDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dbo.Exchange;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.FieldList;
import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.dbo.Quote;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.util.BaseUtil;
import com.metrics.MDOL.util.Streamer;
import com.metrics.MDOL.util.TimeUtil;

public class Runner {
	
	private ApplicationContext _context;
	private QuoteDao quoteDao;
	private SymbolDao symbolDao;
	private FieldDao fieldDao;
	private FieldListDao fieldListDao;
	private ItemDao itemDao;
	private ExchangeDao exchangeDao;
	
	public Runner(ApplicationContext context){
		_context = context;
		quoteDao = (QuoteDao) _context.getBean("quoteDaoProxy");
		symbolDao = (SymbolDao) _context.getBean("symbolDaoProxy");
		fieldDao = (FieldDao) _context.getBean("fieldDaoProxy");
		fieldListDao = (FieldListDao) _context.getBean("fieldListDaoProxy");
		itemDao = (ItemDao) _context.getBean("itemDaoProxy");
		exchangeDao = (ExchangeDao) _context.getBean("exchangeDaoProxy");
	}
	
	public void runner() {
		List<FieldList> list = fieldListDao.getAllList();
		List<Field> fieldAll = fieldDao.getAll();
		List<Symbol> symbols = symbolDao.getAllActiveSymbol();
		List<Item> items = itemDao.getAllActiveItem();
		Timestamp time = TimeUtil.getTimeStamp(new Date());
		
		List<Quote> quoteList = new ArrayList<Quote>();

		for(int i=0 ; i<symbols.size() ; i++){
			Symbol symbol = symbols.get(i);
			String symName = "["+symbol.getName()+"]";
			
			int skip = 0;
			if(symName.contains(".")){
				String rdn = Streamer.map.get(symName+"RDN_EXCHID").get(0).getVal();
				Exchange exc = exchangeDao.getByValue(rdn);
				
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
				//System.err.println(symbol.getName()+" : Closed");
			} else {
				List<Field> fields = new ArrayList<Field>();
				for(Field f : fieldAll){
					if(!f.getName().equals("DISPLY_NAME")){
						Symbol s = f.getSymbol();
						int a = s.getSymbolId();
						int b = symbol.getSymbolId();
						if(a == b){
							fields.add(f);
						}
					}
				}
				
				for(int j=0 ; j<fields.size() ; j++){
					Quote quote = new Quote();
									
					String displayName = "-";
					String value = "0.0";
					String serverDate = null;
					String serverTime = null;
					
					Field field = null;
					for(Field f2 : fieldAll){
						String f2Name = f2.getName();
						int f2Symbol = f2.getSymbol().getSymbolId();
						String name = fields.get(j).getName();
						int id = symbol.getSymbolId();
						if(f2Name.equals(name) && f2Symbol == id){
							field = f2;
							break;
						}
					}
					
					
					int g = 0;
					
					for(int k=0 ; k<items.size() ; k++){
						if(g == 1){
							break;
						}
						for(int l=0 ; l<list.size() ; l++){
							String test = "[" + items.get(k).getName() + "]";
							String test2 = list.get(l).getName();

							String test3 = test+test2;
							
							if(field.getExpression().contains(test3)){
								List<NowLast> nowLast = Streamer.map.get(test+field.getName());
								if(nowLast != null){
									List<NowLast> disp = Streamer.map.get(test+"DSPLY_NAME");
									if(disp != null){
										displayName = disp.get(0).getVal();
									}
									value = nowLast.get(0).getVal().toString();
									List<NowLast> sDate = Streamer.map.get(test+"VALUE_DT1");
									List<NowLast> sTime = Streamer.map.get(test+"VALUE_TS1");
									/*if(symbol.getName().contains(".")){
							    		String date = TimeUtil.getDay(new Date());
							    		String month = TimeUtil.getMonth(new Date());
							    		String year = TimeUtil.getYear(new Date());
							    		
							    		serverDate = date + " " + month + " " + year;
										serverTime = TimeUtil.getTimeString(new Date());

									} else */if(sDate!=null && sTime!=null){
										serverDate = sDate.get(0).getVal();
										serverTime = sTime.get(0).getVal();
									}
								}
								g = 1;
								break;
							}
							
						}
					}
					
					
					for(int k=0 ; k<items.size() ; k++){
						for(int l=0 ; l<list.size() ; l++){
							String test = "["+items.get(k).getName()+"]"+list.get(l).getName();
							if(field.getExpression().contains(test)){
								List<NowLast> nowLast = Streamer.map.get(test);
								String mapValue = "0.0";
								if(nowLast != null){
									mapValue = nowLast.get(0).getVal().toString();
								}
								if(!mapValue.equals("")){
									field.setExpression(field.getExpression().replace(test, mapValue));
								} else {
									field.setExpression("-1");
								}
								
							}
						}
					}
					
					quote.setField(field);
					quote.setSymbol(symbol);
					if(value.equals("")){
						value = "-1";
					}
					quote.setValNow(Double.valueOf(value));
					
					ScriptEngineManager mgr = new ScriptEngineManager();
					ScriptEngine engine = mgr.getEngineByName("JavaScript");
					
					String expression = field.getExpression();
					
					try {
						String last = engine.eval(expression).toString();
						quote.setInsertDate(time);
						quote.setValLast(BaseUtil.round(Double.valueOf(last) , 4));
				    	quote.setDisplayName(displayName);

						if(!value.equals("-1") && !field.getExpression().equals("-1")){
							Timestamp serverUpdateTimeStamp = null;
							if(serverTime != null && serverDate != null && !serverTime.equals("") && !serverDate.equals("") ){
						    	String serverUpdate = serverDate + serverTime;
						    	if(serverUpdate != null && !"".equals(serverUpdate)){
							    	serverUpdateTimeStamp = TimeUtil.getTimeOMM(serverUpdate);
							    	quote.setServerTime(serverUpdateTimeStamp);
						    	}
							} else {
								Timestamp t = quoteDao.getTopServerDateData(symbol);
						    	quote.setServerTime(t);
							}

							quoteList.add(quote);
						} else {
							//System.err.println(symbol.getName()+" : "+field.getName()+" no value");
						}
					} catch (ScriptException e) {
					}
				} 
			}

		}
		quoteDao.saveAll(quoteList);
		System.out.println("Runner : COMPLETE");
		System.out.println(TimeUtil.getTimeStamp(new Date()));
	}

}
