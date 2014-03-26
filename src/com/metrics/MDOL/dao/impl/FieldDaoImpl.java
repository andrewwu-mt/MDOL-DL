package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Symbol;
@SuppressWarnings("unchecked")

public class FieldDaoImpl extends BaseHibernateDaoSupport implements FieldDao{

	@Override
	public void save(Field obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public Field getByFieldAndSymbol(String field, Symbol symbol) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Field.class);
		criteria.add(Restrictions.eq("name", field));
		criteria.add(Restrictions.eq("symbol", symbol));
		List<Field> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Field> getBySymbol(Symbol symbol) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Field.class);
		criteria.setFetchMode("symbol", FetchMode.JOIN);
		criteria.add(Restrictions.eq("symbol", symbol));
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public void update(Field obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Field obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public Field getById(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Field.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.createAlias("s.template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("fieldId", id));
		List<Field> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Field getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Field.class);
		criteria.add(Restrictions.eq("name", name));
		List<Field> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Field> getAll() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Field.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		return criteria.list();
	}


}
