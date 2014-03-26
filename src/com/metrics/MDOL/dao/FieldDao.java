package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Symbol;

public interface FieldDao {
	
	public void save (Field obj);
	
	public void update (Field obj);
	
	public void delete (Field obj);
	
	public Field getById(int id);
	
	public Field getByFieldAndSymbol(String field, Symbol symbol);
	
	public List<Field> getBySymbol(Symbol symbol);
	
	public Field getByName(String name);
	
	public List<Field> getAll();

	
}
