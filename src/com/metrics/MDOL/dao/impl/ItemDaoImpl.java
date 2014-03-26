package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dbo.Item;

@SuppressWarnings("unchecked")
public class ItemDaoImpl extends BaseHibernateDaoSupport implements ItemDao{

	@Override
	public List<Item> getAllActiveItem() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Item.class);
		criteria.add(Restrictions.eq("active", 1));
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public Item getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Item.class);
		criteria.add(Restrictions.eq("name", name));
		List<Item> list = criteria.list();
		if(list.size()!=0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Item getById(int id) {
		// TODO Auto-generated method stub
		return (Item) this.getHibernateTemplate().get(Item.class, id);
	}

	@Override
	public void save(Item obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void delete(Item obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

}
