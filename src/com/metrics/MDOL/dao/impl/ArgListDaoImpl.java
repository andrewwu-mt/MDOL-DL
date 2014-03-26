package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.ArgListDao;
import com.metrics.MDOL.dbo.ArgList;
import com.metrics.MDOL.dbo.Template;

@SuppressWarnings("unchecked")

public class ArgListDaoImpl extends BaseHibernateDaoSupport implements ArgListDao {

	@Override
	public void save(ArgList obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public void update(ArgList obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(ArgList obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public List<ArgList> getListBy(Template template) {
		// TODO Auto-generated method stub
		criteria = createCriteria(ArgList.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("template", template));
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public ArgList getBy(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(ArgList.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("argListId", id));
		criteria.addOrder(Order.asc("name"));
		List<ArgList> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ArgList> getListBy(Template template, String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(ArgList.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("template", template));
		criteria.add(Restrictions.eq("name", name));
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public List<ArgList> getListBy(int templateId) {
		// TODO Auto-generated method stub
		criteria = createCriteria(ArgList.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("temp.templateId", templateId));
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

}
