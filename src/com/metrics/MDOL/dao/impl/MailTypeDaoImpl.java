package com.metrics.MDOL.dao.impl;

import java.util.List;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.MailTypeDao;
import com.metrics.MDOL.dbo.MailType;

@SuppressWarnings("unchecked")
public class MailTypeDaoImpl extends BaseHibernateDaoSupport implements MailTypeDao {

	@Override
	public void update(MailType obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(MailType obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public void save(MailType obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public MailType getMailTypeBy(int id) {
		// TODO Auto-generated method stub
		return (MailType) getHibernateTemplate().get(MailType.class, id);
	}

	@Override
	public List<MailType> getMailTypeListBy() {
		// TODO Auto-generated method stub
		criteria = createCriteria(MailType.class);
		return criteria.list();
	}

	@Override
	public MailType getMailTypeByJoin(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(MailType.class);
		criteria.createAlias("mails", "emails");
		return null;
	}

}
