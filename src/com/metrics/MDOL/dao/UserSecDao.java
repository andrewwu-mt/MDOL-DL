package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.UserSec;

public interface UserSecDao {
	
	public void save(UserSec obj);
	
	public List<UserSec> getAllUserSec();
	
	public UserSec getById(int id);
	
}
