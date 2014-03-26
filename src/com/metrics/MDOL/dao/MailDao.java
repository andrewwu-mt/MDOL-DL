package com.metrics.MDOL.dao;

import java.util.List;

import com.metrics.MDOL.dbo.Mail;
import com.metrics.MDOL.dbo.MailType;

public interface MailDao {
	public void save(Mail obj);
	
	public void update(Mail obj);
	
	public void delete(Mail obj);
	
	public Mail getMailBy(int id);
	
	public List<Mail> getMailListBy();
	
	public List<Mail> getMailListBy(MailType mailType);
}
