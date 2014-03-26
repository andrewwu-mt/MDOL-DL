package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.HtmlType;

public interface HtmlTypeDao {

	public List<HtmlType> getAllList();
	
	public HtmlType getById(int id);
	
}
