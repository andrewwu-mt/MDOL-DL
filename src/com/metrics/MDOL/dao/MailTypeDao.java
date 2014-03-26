package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.MailType;

public interface MailTypeDao {
	public void update(MailType obj);
	
	public void delete(MailType obj);
	
	public void save(MailType obj);
	
	public MailType getMailTypeBy(int id);
	
	public List<MailType> getMailTypeListBy();
	
	public MailType getMailTypeByJoin(int id);
}
