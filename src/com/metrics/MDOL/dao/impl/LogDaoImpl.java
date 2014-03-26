package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.LogDao;
import com.metrics.MDOL.dbo.Log;
import com.metrics.MDOL.dbo.User;

@SuppressWarnings("unchecked")
public class LogDaoImpl extends BaseHibernateDaoSupport implements LogDao{

	@Override
	public void save(Log obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Log obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Log obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public List<Log> getAllLog() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Log.class);
		criteria.setFetchMode("logFunc", FetchMode.JOIN);
		criteria.setFetchMode("logType", FetchMode.JOIN);
		criteria.setFetchMode("user", FetchMode.JOIN);
		criteria.addOrder(Order.desc("insertDate"));
		return criteria.list();
	}

	@Override
	public List<String> groupByDate() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Log.class);	
		ProjectionList projList = Projections.projectionList();
	    criteria.addOrder(Order.desc("insertDate"));
	    projList.add(Projections.alias(Projections.sqlGroupProjection("CONVERT(insert_date, CHAR(10)) As d", "d", new String[] {"d"}, new Type[] {Hibernate.STRING}), "d"));
	    criteria.setProjection(projList);
	    return criteria.list();

	}

	@Override
	public List<Log> getByDate(String date) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Log.class);
		criteria.setFetchMode("logFunc", FetchMode.JOIN);
		criteria.setFetchMode("logType", FetchMode.JOIN);
		criteria.setFetchMode("user", FetchMode.JOIN);
		criteria.add(Restrictions.sqlRestriction("lower({alias}.insert_date) like lower(?)", "%" + date + "%", Hibernate.STRING));
		criteria.addOrder(Order.desc("insertDate"));
		return criteria.list();
	}

	@Override
	public List<String> getByUserFromTo(User user, String from, String to) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Log.class);
		
		criteria.add(Restrictions.eq("user", user));
		if(from != null && !"".equals(from) && to != null && !"".equals(to)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') >= '" + from + "' AND DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') <= '" + to +"'"));
		}
		ProjectionList projList = Projections.projectionList();
		criteria.addOrder(Order.desc("insertDate"));
	    projList.add(Projections.alias(Projections.sqlGroupProjection("CONVERT(insert_date, CHAR(10)) As d", "d", new String[] {"d"}, new Type[] {Hibernate.STRING}), "d"));
	    criteria.setProjection(projList);
		
		return criteria.list();
	}

	@Override
	public List<Log> getByUserDate(User user, String date) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Log.class);	
		criteria.add(Restrictions.eq("user", user));
		criteria.setFetchMode("logFunc", FetchMode.JOIN);
		criteria.setFetchMode("logType", FetchMode.JOIN);
		criteria.setFetchMode("user", FetchMode.JOIN);
		criteria.add(Restrictions.sqlRestriction("lower({alias}.insert_date) like lower(?)", "%" + date + "%", Hibernate.STRING));
		criteria.addOrder(Order.desc("insertDate"));
	    return criteria.list();
	}

}
