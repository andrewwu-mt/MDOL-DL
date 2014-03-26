package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.ArgList;
import com.metrics.MDOL.dbo.Template;

public interface ArgListDao {
	public void save(ArgList obj);
	
	public void update(ArgList obj);
	
	public void delete(ArgList obj);
	
	public List<ArgList> getListBy(Template template);
	
	public List<ArgList> getListBy(int templateId);
	
	public List<ArgList> getListBy(Template template, String name);
	
	public ArgList getBy(int id);
}
