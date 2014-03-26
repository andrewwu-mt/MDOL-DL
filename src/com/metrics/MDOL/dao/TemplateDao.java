package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Template;

public interface TemplateDao {
	
	public void save(Template obj);
	
	public void update(Template obj);
	
	public void delete(Template obj);
	
	public Template getById(int id);
	
	public Template getByName(String name);
	
	public List<Template> getListBy();
}
