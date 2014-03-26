package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.ExchangeDao;
import com.metrics.MDOL.dbo.Exchange;

@SuppressWarnings("unchecked")
public class ExchangeDaoImpl extends BaseHibernateDaoSupport implements ExchangeDao{

	@Override
	public void save(Exchange obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Exchange> getAll() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Exchange.class);
		return criteria.list();
	}

	@Override
	public void delete(Exchange obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public Exchange getById(int id) {
		// TODO Auto-generated method stub
		return (Exchange) this.getHibernateTemplate().get(Exchange.class, id);
	}

	@Override
	public Exchange getByValue(String value) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Exchange.class);
		criteria.add(Restrictions.eq("value", value));
		List<Exchange> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

}
