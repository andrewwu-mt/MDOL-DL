package com.metrics.MDOL.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.PageUpdateDao;
import com.metrics.MDOL.dbo.PageUpdate;

@SuppressWarnings("unchecked")

public class PageUpdateDaoImpl extends BaseHibernateDaoSupport implements PageUpdateDao{

	@Override
	public void save(PageUpdate obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<PageUpdate> getNewestList() {
		// TODO Auto-generated method stub
		criteria = createCriteria(PageUpdate.class);
		criteria.addOrder(Order.desc("insertDate"));
		List<PageUpdate> list = criteria.list();
		Timestamp time = list.get(0).getInsertDate();
		criteria.add(Restrictions.eq("insertDate", time));
		
		return criteria.list();
	}

}
