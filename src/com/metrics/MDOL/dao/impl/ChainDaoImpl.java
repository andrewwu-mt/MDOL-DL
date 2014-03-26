package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.ChainDao;
import com.metrics.MDOL.dbo.Chain;

@SuppressWarnings("unchecked")
public class ChainDaoImpl extends BaseHibernateDaoSupport implements ChainDao{

	@Override
	public void save(Chain obj) {
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Chain> getAllChain() {
		criteria = createCriteria(Chain.class);
		if(criteria.list().size() != 0){
			return criteria.list();
		} else {
			return null;
		}
		
	}

	@Override
	public Chain getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Chain.class);
		criteria.add(Restrictions.eq("name", name));
		List<Chain> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Chain> groupByName() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Chain.class);
		ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.alias(Projections.sqlGroupProjection("CONVERT(name, CHAR(80)) As d", "d", new String[] {"d"}, new Type[] {Hibernate.STRING}), "d"));
	    criteria.setProjection(projList);
	    return criteria.list();
	}
	

}
