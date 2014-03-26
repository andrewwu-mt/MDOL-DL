package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.SymbolArgDao;
import com.metrics.MDOL.dbo.Symbol;
import com.metrics.MDOL.dbo.SymbolArg;

@SuppressWarnings("unchecked")
public class SymbolArgDaoImpl extends BaseHibernateDaoSupport implements SymbolArgDao {

	@Override
	public void save(SymbolArg obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public void update(SymbolArg obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(SymbolArg obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public SymbolArg getBy(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(SymbolArg.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("symbolArgId", id));
		List<SymbolArg> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<SymbolArg> getListBy(int symbolId, String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(SymbolArg.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("s.symbolId", symbolId));
		if(name != null && !"".equals(name)) {
			criteria.add(Restrictions.eq("name", name));
		}
		return criteria.list();
	}

	@Override
	public List<SymbolArg> getListBy(Symbol symbol, String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(SymbolArg.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("symbol", symbol));
		if(name != null && !"".equals(name)) {
			criteria.add(Restrictions.eq("name", name));
		}
		return criteria.list();
	}

	@Override
	public List<SymbolArg> getListBy(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(SymbolArg.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("name", name));
		return criteria.list();
	}

}
