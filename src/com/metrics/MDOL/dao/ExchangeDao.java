package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Exchange;

public interface ExchangeDao {

	public void save(Exchange obj);
	
	public void delete(Exchange obj);
	
	public List<Exchange> getAll();

	public Exchange getById(int id);
	
	public Exchange getByValue(String value);
	
}
