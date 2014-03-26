package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.User;

public interface UserDao {
	
	
	public void save (User obj);
	
	public void update (User obj);
	
	public void delete (User obj);
	
	public User getUserById(int id);
	
	public List<User> getAllUser();
	
	public List<User> getOwnUser(int id);
	
	public User getByEmail(String email);
	
	public User getByName(String name);
	
}
