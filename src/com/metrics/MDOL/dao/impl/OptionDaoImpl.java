package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.OptionDao;
import com.metrics.MDOL.dbo.Option;

@SuppressWarnings("unchecked")
public class OptionDaoImpl extends BaseHibernateDaoSupport implements OptionDao {

	@Override
	public void save(Option obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Option obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Option obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public Option getOptionBy(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Option.class);
		criteria.add(Restrictions.eq("optionId", id));
		List<Option> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Option> getOptionListBy() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Option.class);;
		return criteria.list();
	}

}
