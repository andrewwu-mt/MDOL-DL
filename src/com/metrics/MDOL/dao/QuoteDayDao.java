package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.QuoteDay;

public interface QuoteDayDao {

	public void truncate();
	
	public void saveAll(List<QuoteDay> list);
	
	public List<QuoteDay> getAll();
	
}
