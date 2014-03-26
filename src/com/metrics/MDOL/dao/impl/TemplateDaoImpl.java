package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.TemplateDao;
import com.metrics.MDOL.dbo.Template;

@SuppressWarnings("unchecked")
public class TemplateDaoImpl extends BaseHibernateDaoSupport implements TemplateDao {

	@Override
	public void save(Template obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}
	
	@Override
	public void update(Template obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Template obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public Template getById(int id) {
		// TODO Auto-generated method stub
		return (Template) this.getHibernateTemplate().get(Template.class, id);
	}

	@Override
	public Template getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Template.class);
		criteria.add(Restrictions.eq("name", name));
		List<Template> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else{
			return null;
		}
	}

	@Override
	public List<Template> getListBy() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Template.class);
		return criteria.list();
	}
}
