package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.dbo.Symbol;

public interface SymbolDao {
	
	public void save (Symbol obj);
	
	public void update(Symbol obj);
	
	public void delete(Symbol obj);
	
	public List<Symbol> getAllSymbol();
	
	public Symbol getById(int id);
	
	public Symbol getByName(String name);
	
	public List<Symbol> getAllActiveSymbol();
	
	public Symbol getByNameForApp(String name);
	
	public Symbol getByItem(Item item);
	
	public List<String> getListString();	
	
	public boolean checkSymbol(String name);
	
}
