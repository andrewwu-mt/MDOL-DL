package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Log;
import com.metrics.MDOL.dbo.User;

public interface LogDao {
	
	public void save(Log obj);
	
	public void update(Log obj);
	
	public void delete(Log obj);
	
	public List<Log> getAllLog();
	
	public List<String> groupByDate();
	
	public List<Log> getByDate(String date);
	
	public List<String> getByUserFromTo(User user, String from, String to);
	
	public List<Log> getByUserDate(User user, String date);
	
}
