package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Template;
import com.metrics.MDOL.dbo.TemplateArg;

public interface TemplateArgDao {
	
	public void save (TemplateArg obj);
	
	public void update(TemplateArg obj);
	
	public void delete(TemplateArg obj);
	
	public TemplateArg getBy(int id);
	
	public List<TemplateArg> getByTemp(Template template);
	
	public List<TemplateArg> getAll();
	
	public TemplateArg getByFieldExp(String field , String exp);
	
	public TemplateArg getByTempAndField(Template template , String field);

}
