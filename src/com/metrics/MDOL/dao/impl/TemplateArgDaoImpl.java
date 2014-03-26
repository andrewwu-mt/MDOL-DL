package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.TemplateArgDao;
import com.metrics.MDOL.dbo.Template;
import com.metrics.MDOL.dbo.TemplateArg;

@SuppressWarnings("unchecked")
public class TemplateArgDaoImpl extends BaseHibernateDaoSupport implements TemplateArgDao{

	@Override
	public void save(TemplateArg obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<TemplateArg> getByTemp(Template template) {
		// TODO Auto-generated method stub
		criteria = createCriteria(TemplateArg.class);
		criteria.add(Restrictions.eq("template", template));
		criteria.addOrder(Order.asc("field"));
		return criteria.list();
	}

	@Override
	public List<TemplateArg> getAll() {
		// TODO Auto-generated method stub
		criteria = createCriteria(TemplateArg.class);
		criteria.setFetchMode("template", FetchMode.JOIN);
		return criteria.list();
	}	
		
	@Override
	public void update(TemplateArg obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(TemplateArg obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public TemplateArg getBy(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(TemplateArg.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("templateArgId", id));
		List<TemplateArg> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public TemplateArg getByFieldExp(String field , String exp) {
		// TODO Auto-generated method stub
		criteria = createCriteria(TemplateArg.class);
		criteria.add(Restrictions.eq("field" , field));
		criteria.add(Restrictions.eq("expression" , exp));
		List<TemplateArg> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public TemplateArg getByTempAndField(Template template, String field) {
		// TODO Auto-generated method stub
		criteria = createCriteria(TemplateArg.class);
		criteria.add(Restrictions.eq("template", template));
		criteria.add(Restrictions.eq("field", field));
		List<TemplateArg> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	

}
