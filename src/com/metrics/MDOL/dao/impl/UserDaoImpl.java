package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.UserDao;
import com.metrics.MDOL.dbo.User;

public class UserDaoImpl extends BaseHibernateDaoSupport implements UserDao{
	
	public void save(User obj){
		this.getHibernateTemplate().save(obj);
	}
	
	@Override
	public void update(User obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(User obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	public User getUserById(int id){
		return (User) getHibernateTemplate().get(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUser(){
		criteria = createCriteria(User.class);
		criteria.setFetchMode("userSec" , FetchMode.JOIN);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		criteria = createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));	
		List<User> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getOwnUser(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(User.class);
		criteria.setFetchMode("userSec" , FetchMode.JOIN);
		criteria.add(Restrictions.eq("userId", id));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(User.class);
		criteria.add(Restrictions.eq("name", name));
		List<User> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else {
			return null;
		}
	}


	
}
