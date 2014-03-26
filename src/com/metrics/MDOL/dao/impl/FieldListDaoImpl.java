package com.metrics.MDOL.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.FieldListDao;
import com.metrics.MDOL.dbo.FieldList;

@SuppressWarnings("unchecked")
public class FieldListDaoImpl extends BaseHibernateDaoSupport implements FieldListDao{

	@Override
	public List<FieldList> getAllList() {
		// TODO Auto-generated method stub
		criteria = createCriteria(FieldList.class);		
		return criteria.list();
	}

	@Override
	public List<String> getListString() {
		// TODO Auto-generated method stub
		criteria = createCriteria(FieldList.class);
		List<String> strList = new ArrayList<String>();
		List<FieldList> list = criteria.list();
		for(FieldList a : list){
			strList.add(a.getName());
		}
		
		return strList;
	}

	@Override
	public void save(FieldList obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public FieldList getById(int id) {
		// TODO Auto-generated method stub
		return (FieldList) this.getHibernateTemplate().get(FieldList.class, id);
	}

	@Override
	public void delete(FieldList obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public FieldList getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(FieldList.class);
		criteria.add(Restrictions.eq("name", name));
		List<FieldList> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
