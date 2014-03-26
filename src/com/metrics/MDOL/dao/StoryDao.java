package com.metrics.MDOL.dao;

import java.sql.Timestamp;
import java.util.List;

import com.metrics.MDOL.dbo.Story;

public interface StoryDao {
	public void save(Story obj);
	
	public void update(Story obj);
	
	public void delete(Story obj);
	
	public List<Story> getBy(String headline, Timestamp time);
	
	public List<Story> betBy();
	
	public Story getBy(int id);
	
	public List<Story> getBySize(int size);
	
	public List<Story> getBySizeAndCode(int size , String code);
	
	public List<Story> getBySizeAndCompany(int size, String company);
	
	public List<Story> getBySizeAndCodeAndCompany(int size , String code, String company);
	
}
