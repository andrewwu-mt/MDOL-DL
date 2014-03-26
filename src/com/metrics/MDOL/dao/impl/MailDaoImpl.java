package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.MailDao;
import com.metrics.MDOL.dbo.Mail;
import com.metrics.MDOL.dbo.MailType;

@SuppressWarnings("unchecked")
public class MailDaoImpl extends BaseHibernateDaoSupport implements MailDao {

	@Override
	public void save(Mail obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Mail obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Mail obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public Mail getMailBy(int id) {
		// TODO Auto-generated method stub
		return (Mail) getHibernateTemplate().get(Mail.class, id);
	}

	@Override
	public List<Mail> getMailListBy() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Mail.class);
		criteria.createAlias("mailType", "type", Criteria.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public List<Mail> getMailListBy(MailType mailType) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Mail.class);
		criteria.createAlias("mailType", "type", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("mailType", mailType));
		return criteria.list();
	}
	
}
