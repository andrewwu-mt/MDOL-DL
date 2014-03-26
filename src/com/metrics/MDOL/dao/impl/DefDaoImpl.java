package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.DefDao;
import com.metrics.MDOL.dbo.Def;
@SuppressWarnings("unchecked")
public class DefDaoImpl extends BaseHibernateDaoSupport implements DefDao {

	@Override
	public void save(Def def) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(def);
	}

	@Override
	public void update(Def def) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(def);
	}

	@Override
	public void delete(Def def) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(def);
	}

	@Override
	public Def getDefBy(int defId) {
		// TODO Auto-generated method stub
		return (Def) getHibernateTemplate().get(Def.class, defId);
	}

	@Override
	public List<Def> getAllList() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Def.class);
		return criteria.list();
	}

	@Override
	public Def getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Def.class);
		criteria.add(Restrictions.eq("name", name));
		List<Def> def = criteria.list();
		if(def.size() != 0){
			return def.get(0);
		} else {
			return null;
		}

	}

}
