package com.metrics.MDOL.dao.impl;

import java.util.List;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.CurrencyDao;
import com.metrics.MDOL.dbo.Currency;

@SuppressWarnings("unchecked")
public class CurrencyDaoImpl extends BaseHibernateDaoSupport implements CurrencyDao{

	@Override
	public void save(Currency obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Currency> getAll() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Currency.class);
		return criteria.list();
	}

	@Override
	public void delete(Currency obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public Currency getById(int id) {
		// TODO Auto-generated method stub
		return (Currency) this.getHibernateTemplate().get(Currency.class, id);
	}

}
