package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.LogTypeDao;
import com.metrics.MDOL.dbo.LogType;

public class LogTypeDaoImpl extends BaseHibernateDaoSupport implements LogTypeDao{

	@SuppressWarnings("unchecked")
	@Override
	public LogType getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(LogType.class);
		criteria.add(Restrictions.eq("name", name));
		List<LogType> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

}
