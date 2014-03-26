package com.metrics.MDOL.dao;


import java.sql.Timestamp;
import java.util.List;

import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Quote;
import com.metrics.MDOL.dbo.Symbol;

public interface QuoteDao {
	
	public void saveAll(List<Quote> list);
	
	public void save (Quote obj);
	
	public void update (Quote obj);
	
	public List<Quote> groupByField();
	
	public Quote getBySymbolAndField(Symbol symbol , Field field);
	
	public List<Quote> getBySymbol(Symbol symbol);
	
	public List<Quote> getByInstrument(String instrument, String dateStart, String dateEnd);
	
	public List<Quote> getGroupByInsertDate(String instrument, String dateStart, String dateEnd);
	
	public List<Quote> getNewestList();
	
	public List<Quote> getNewestListBy(String symbolName, Timestamp time);
	
	public Timestamp getTopInsertDateData(String symbolName);
	
	public Timestamp getTopServerDateData(Symbol symbol);
	
	public Quote getNewestBySymbol(String name);
	
	public List<Quote> getByInstrument2(String instrument, String dateStart, String dateEnd, int freq);
	
	public List<Quote> getByInstrument2(String instrument, String dateStart, String dateEnd, int freq, Timestamp time);
	
}
