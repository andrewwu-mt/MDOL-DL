package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.HtmlDao;
import com.metrics.MDOL.dbo.Html;
import com.metrics.MDOL.dbo.HtmlType;

@SuppressWarnings("unchecked")
public class HtmlDaoImpl extends BaseHibernateDaoSupport implements HtmlDao{

	@Override
	public void save(Html obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public List<Html> getAllList() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Html.class);
		criteria.setFetchMode("htmlType", FetchMode.JOIN);
		return criteria.list();
	}

	@Override
	public Html getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Html.class);
		criteria.add(Restrictions.eq("name", name));
		List<Html> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public void update(Html obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Html obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public Html getById(int id) {
		// TODO Auto-generated method stub
		return (Html) this.getHibernateTemplate().get(Html.class, id);
	}

	@Override
	public List<Html> getListByType(HtmlType htmlType) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Html.class);
		criteria.setFetchMode("htmlType", FetchMode.JOIN);
		if(htmlType != null) criteria.add(Restrictions.eq("htmlType", htmlType));
		return criteria.list();
	}

	@Override
	public List<Html> getListByType(HtmlType htmlType, String type, int defMark) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Html.class);
		criteria.createAlias("htmlType", "t", Criteria.LEFT_JOIN);
		if(htmlType != null) criteria.add(Restrictions.eq("htmlType", htmlType));
		if(type != null && !"".equals(type)) criteria.add(Restrictions.eq("t.name", type));
		if(defMark == 0 || defMark == 1) criteria.add(Restrictions.eq("defMark", defMark));
		return criteria.list();
	}

	@Override
	public boolean checkName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Html.class);
		criteria.add(Restrictions.eq("name", name));
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("name"));
		criteria.setProjection(projList);
		if((String) criteria.uniqueResult() != null){
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
