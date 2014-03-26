package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

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

}
