package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Chain;
import com.metrics.MDOL.dbo.Link;

public interface LinkDao {
	
	public void save(Link obj);
	
	public List<Link> getLinkByChain(Chain chain);
	
	public List<String> groupByName();

}
