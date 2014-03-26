package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Option;

public interface OptionDao {
	public void save(Option obj);
	
	public void update(Option obj);
	
	public void delete(Option obj);
	
	public Option getOptionBy(int id);
	
	public List<Option> getOptionListBy();
	
}
