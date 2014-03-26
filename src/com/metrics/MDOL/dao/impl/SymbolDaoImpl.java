package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dbo.Item;
import com.metrics.MDOL.dbo.Symbol;

@SuppressWarnings("unchecked")
public class SymbolDaoImpl extends BaseHibernateDaoSupport implements SymbolDao{

	@Override
	public void save(Symbol obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Symbol> getAllSymbol() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.setFetchMode("template", FetchMode.JOIN);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public Symbol getById(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("symbolId", id));
		List<Symbol> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Symbol getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.createAlias("template", "temp", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("name", name));
		List<Symbol> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else{
			return null;
		}
	}

	@Override
	public void update(Symbol obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Symbol obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public List<Symbol> getAllActiveSymbol() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.add(Restrictions.eq("active", 1));
		criteria.setFetchMode("item", FetchMode.JOIN);
		return criteria.list();
	}

	@Override
	public Symbol getByNameForApp(String name) {
		// TODO Auto-generated method stub
		List<Symbol> list =  this.getHibernateTemplate().find("from Symbol where name like "+name);
		return list.get(0);
	}

	@Override
	public Symbol getByItem(Item item) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.add(Restrictions.eq("item", item));
		List<Symbol> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<String> getListString() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("name"));
		criteria.setProjection(projList);
		return criteria.list();
	}

	@Override
	public boolean checkSymbol(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Symbol.class);
		criteria.add(Restrictions.eq("name", name));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("name"));
		criteria.setProjection(projList);
		if((String) criteria.uniqueResult() != null){
			return true;
		} else {
			return false;
		}
	}

}
