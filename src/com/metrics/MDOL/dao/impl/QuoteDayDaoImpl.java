package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.QuoteDayDao;
import com.metrics.MDOL.dbo.QuoteDay;

public class QuoteDayDaoImpl extends BaseHibernateDaoSupport implements QuoteDayDao{

	@Override
	public void truncate() {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createSQLQuery("TRUNCATE mdol_quote_day");
		query.executeUpdate();
		session.close();
	}

	@Override
	public void saveAll(List<QuoteDay> list) {
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public List<QuoteDay> getAll() {
		criteria = createCriteria(QuoteDay.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.createAlias("field", "f", Criteria.LEFT_JOIN);
		return criteria.list();
	}

	@Override
	public QuoteDay getBySymbolAndField(String symbol, String field) {
		// TODO Auto-generated method stub
		criteria = createCriteria(QuoteDay.class);
		criteria.createAlias("symbol", "s", Criteria.LEFT_JOIN);
		criteria.createAlias("field", "f", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("s.name", symbol));
		criteria.add(Restrictions.eq("f.name", field));
		return (QuoteDay) criteria.uniqueResult();
	}

	@Override
	public void save(QuoteDay obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void update(QuoteDay obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

}
