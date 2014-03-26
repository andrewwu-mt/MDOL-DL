package com.metrics.MDOL.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.TsDao;
import com.metrics.MDOL.dbo.Ts;

@SuppressWarnings("unchecked")
public class TsDaoImpl extends BaseHibernateDaoSupport implements TsDao{
	String[] columes = {
			"bid", "open", "high", "low", "ask", "highBid",
			"lowBid", "openAsk", "highAsk", "lowAsk", "close", "vol", "last"	
	};
	
	@Override
	public void save(Ts obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public Ts getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		criteria.add(Restrictions.eq("ts", name));
		List<Ts> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Ts getLatestTime(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		
		//Criteria c = createCriteria(Ts.class);
		//c.add(Restrictions.eq("ts", name));
		//c.addOrder(Order.desc("date"));
		//List<Ts> l = c.list();
		//Timestamp time = l.get(0).getDate();
		
		//criteria.add(Restrictions.eq("date", time));
		criteria.add(Restrictions.eq("ts", name));
		criteria.addOrder(Order.desc("date"));
		List<Ts> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Ts> getListBy(String instrument, String dateStart,
			String dateEnd) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		criteria.add(Restrictions.eq("ts", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.date, '%Y-%m-%d') >= '" + dateStart + "' AND DATE_FORMAT({alias}.date, '%Y-%m-%d') <= '" + dateEnd +"'"));
		}
		criteria.add(Restrictions.or(Restrictions.not(Restrictions.like("bid", "-%")), Restrictions.not(Restrictions.like("close", "-%"))));
		//criteria.add(Restrictions.or(Restrictions.not(Restrictions.like("bid", "-%")), Restrictions.or(Restrictions.not(Restrictions.like("ask", "-%")), Restrictions.not(Restrictions.like("last", "-%")))));
		criteria.addOrder(Order.asc("date"));
		return criteria.list();
	}
	
	@Override
	public List<Ts> getListByDesc(String instrument, String dateStart,
			String dateEnd) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		criteria.add(Restrictions.eq("ts", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.date, '%Y-%m-%d') >= '" + dateStart + "' AND DATE_FORMAT({alias}.date, '%Y-%m-%d') <= '" + dateEnd +"'"));
		}
		criteria.addOrder(Order.desc("date"));
		return criteria.list();
	}

	@Override
	public List<Ts> getAll() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		return criteria.list();
	}

	@Override
	public void delete(Ts obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public List<Ts> getListByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		criteria.add(Restrictions.eq("ts", name));
		return criteria.list();
	}

	@Override
	public Ts getByNameAndDate(String name, Timestamp time) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Ts.class);
		criteria.add(Restrictions.eq("date", time));
		criteria.add(Restrictions.eq("ts", name));
		criteria.setMaxResults(1);
		List<Ts> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void update(Ts obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void saveAll(List<Ts> list) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public void updateAll(List<Ts> list) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	
	
}
