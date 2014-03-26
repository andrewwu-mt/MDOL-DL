package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Chain;

public interface ChainDao {
	
	public void save (Chain obj);
	
	public List<Chain> getAllChain();
	
	public Chain getByName(String name);
	
	public List<Chain> groupByName();
	
}
