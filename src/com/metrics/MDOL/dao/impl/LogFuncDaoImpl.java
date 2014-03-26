package com.metrics.MDOL.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.LogFuncDao;
import com.metrics.MDOL.dbo.LogFunc;

public class LogFuncDaoImpl extends BaseHibernateDaoSupport implements LogFuncDao{

	@SuppressWarnings("unchecked")
	@Override
	public LogFunc getByName(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(LogFunc.class);
		criteria.add(Restrictions.eq("name", name));
		List<LogFunc> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}
}
