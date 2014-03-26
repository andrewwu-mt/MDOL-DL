package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.PageUpdate;

public interface PageUpdateDao {

	public void save(PageUpdate obj);
	
	public List<PageUpdate> getNewestList();
	
}
