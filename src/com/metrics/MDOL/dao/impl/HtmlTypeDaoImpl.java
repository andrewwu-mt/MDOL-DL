package com.metrics.MDOL.dao.impl;

import java.util.List;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.HtmlTypeDao;
import com.metrics.MDOL.dbo.HtmlType;

@SuppressWarnings("unchecked")
public class HtmlTypeDaoImpl extends BaseHibernateDaoSupport implements HtmlTypeDao{

	@Override
	public List<HtmlType> getAllList() {
		// TODO Auto-generated method stub
		criteria = createCriteria(HtmlType.class);
		return criteria.list();
	}

	@Override
	public HtmlType getById(int id) {
		// TODO Auto-generated method stub
		return (HtmlType) this.getHibernateTemplate().get(HtmlType.class, id);
	}

}
