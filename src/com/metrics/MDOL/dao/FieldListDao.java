package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.FieldList;

public interface FieldListDao {

	public void save(FieldList obj);
	
	public void delete(FieldList obj);
	
	public List<FieldList> getAllList();
	
	public List<String> getListString(); 
	
	public FieldList getById(int id);
	
	public FieldList getByName(String name);
	
}
