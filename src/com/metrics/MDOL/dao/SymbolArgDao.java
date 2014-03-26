package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.dbo.SymbolArg;

public interface SymbolArgDao {
	public void save(SymbolArg obj);
	
	public void update(SymbolArg obj);
	
	public void delete(SymbolArg obj);
	
	public SymbolArg getBy(int id);
	
	public List<SymbolArg> getListBy(int symbolId, String name);
	
	public List<SymbolArg> getListBy(Symbol symbol, String name);
	
	public List<SymbolArg> getListBy(String name);
	
}
