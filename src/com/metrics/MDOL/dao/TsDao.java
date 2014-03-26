package com.metrics.MDOL.dao;

import java.sql.Timestamp;
import java.util.List;

import com.metrics.MDOL.dbo.Ts;

public interface TsDao {
	
	public void save(Ts obj);
	
	public void update(Ts obj);
	
	public void delete(Ts obj);
	
	public List<Ts> getAll();
	
	public Ts getByName(String name);
	
	public List<Ts> getListByName(String name);
	
	public Ts getLatestTime(String name);
	
	public List<Ts> getListBy(String instrument, String dateStart, String dateEnd);
	
	public List<Ts> getListByDesc(String instrument, String dateStart, String dateEnd);
	
	public Ts getByNameAndDate(String name, Timestamp time);
	
	public void saveAll(List<Ts> list);
	
	public void updateAll(List<Ts> list);

}
