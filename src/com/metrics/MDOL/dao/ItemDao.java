package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Item;

public interface ItemDao {

	public void save(Item obj);
	
	public void delete(Item obj);
	
	public List<Item> getAllActiveItem();
	
	public Item getByName(String name);
	
	public Item getById(int id);
}
