package com.metrics.MDOL.dao.impl;

import java.util.List;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.UserSecDao;
import com.metrics.MDOL.dbo.UserSec;

public class UserSecDaoImpl extends BaseHibernateDaoSupport implements UserSecDao{

	@Override
	public void save(UserSec obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserSec> getAllUserSec() {
		// TODO Auto-generated method stub
		criteria = createCriteria(UserSec.class);
		return criteria.list();
	}

	@Override
	public UserSec getById(int id) {
		// TODO Auto-generated method stub
		return (UserSec) this.getHibernateTemplate().get(UserSec.class, id);
	}

}
