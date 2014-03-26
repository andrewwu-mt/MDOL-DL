package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.LinkDao;
import com.metrics.MDOL.dbo.Chain;
import com.metrics.MDOL.dbo.Link;
@SuppressWarnings("unchecked")

public class LinkDaoImpl extends BaseHibernateDaoSupport implements LinkDao{

	@Override
	public void save(Link obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Link> getLinkByChain(Chain chain) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Link.class);
		criteria.add(Restrictions.eq("chain", chain));
		return criteria.list();
	}

	@Override
	public List<String> groupByName() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Link.class);
		ProjectionList projList = Projections.projectionList();
	    projList.add(Projections.alias(Projections.sqlGroupProjection("CONVERT(chain, CHAR(80)) As d", "d", new String[] {"d"}, new Type[] {Hibernate.STRING}), "d"));
	    criteria.setProjection(projList);
	    return criteria.list();
	}

}
