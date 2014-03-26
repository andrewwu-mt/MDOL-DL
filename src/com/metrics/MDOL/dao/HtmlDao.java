package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Html;
import com.metrics.MDOL.dbo.HtmlType;

public interface HtmlDao {
	
	public void save (Html obj);
	
	public void update (Html obj);
	
	public void delete (Html obj);
	
	public List<Html> getAllList();
	
	public Html getByName(String name);
	
	public boolean checkName(String name);
	
	public Html getById(int id);
	
	public List<Html> getListByType(HtmlType htmlType);
	
	public List<Html> getListByType(HtmlType htmlType, String type, int defMark);

}
