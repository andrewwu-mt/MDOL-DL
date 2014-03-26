package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Currency;

public interface CurrencyDao {

	public void save(Currency obj);
	
	public void delete(Currency obj);
	
	public List<Currency> getAll();
	
	public Currency getById(int id);
	
}
