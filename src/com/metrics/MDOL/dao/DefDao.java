package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Def;

public interface DefDao {
	public void save(Def def);
	
	public void update(Def def);
	
	public void delete(Def def);
	
	public Def getDefBy(int defId);
	
	public List<Def> getAllList();
	
	public Def getByName(String name);
	
}
