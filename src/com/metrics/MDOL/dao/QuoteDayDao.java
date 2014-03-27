package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.QuoteDay;

public interface QuoteDayDao {

	public void truncate();
	
	public void save(QuoteDay obj);
	
	public void update(QuoteDay obj);
	
	public void saveAll(List<QuoteDay> list);
	
	public List<QuoteDay> getAll();
	
	public QuoteDay getBySymbolAndField(String symbol, String field);
	
}
