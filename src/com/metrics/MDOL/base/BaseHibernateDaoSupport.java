package com.metrics.MDOL.base;

import org.hibernate.Criteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseHibernateDaoSupport extends HibernateDaoSupport{
	
	protected Criteria criteria;
	
	public Criteria createCriteria(Class<?> clazz){
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(clazz);		
	}
	
}
