package com.metrics.MDOL.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.QuoteDao;
import com.metrics.MDOL.dbo.Field;
import com.metrics.MDOL.dbo.Quote;
import com.metrics.MDOL.dbo.Symbol;

@SuppressWarnings("unchecked")
public class QuoteDaoImpl extends BaseHibernateDaoSupport implements QuoteDao{

	@Override
	public void save(Quote obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Quote obj) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(obj);
	}

	@Override
	public List<Quote> groupByField() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		Criteria c = createCriteria(Quote.class);
		c.addOrder(Order.desc("insertDate"));
		List<Quote> list = c.list();
		Timestamp time = list.get(0).getInsertDate();
		
		criteria.add(Restrictions.eq("insertDate", time));
		
	    return criteria.list();
	}

	@Override
	public Quote getBySymbolAndField(Symbol symbol, Field field) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		criteria.setFetchMode("symbol", FetchMode.JOIN);
		criteria.setFetchMode("field", FetchMode.JOIN);
		criteria.add(Restrictions.eq("symbol", symbol));
		criteria.add(Restrictions.eq("field", field));
		criteria.addOrder(Order.desc("insertDate"));
		criteria.setMaxResults(1);
		List<Quote> lists = criteria.list();
		if(lists.size() != 0){
			return lists.get(0);
		} else{
			return null;
		}
	}

	@Override
	public List<Quote> getBySymbol(Symbol symbol) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		criteria.setFetchMode("symbol", FetchMode.JOIN);
		criteria.setFetchMode("field", FetchMode.JOIN);

		Criteria c = createCriteria(Quote.class);
		c.addOrder(Order.desc("insertDate"));
		c.add(Restrictions.eq("symbol", symbol));
		List<Quote> list = c.list();
		if(list.size() != 0){
			Timestamp time = list.get(0).getInsertDate();
			
			criteria.add(Restrictions.eq("insertDate", time));
			
			criteria.add(Restrictions.eq("symbol", symbol));
			
			return criteria.list();
		} else {
			return null;
		}
		
		
	}

	@Override
	public List<Quote> getByInstrument(String instrument, String dateStart,
			String dateEnd) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		criteria.createAlias("symbol", "sym" , Criteria.LEFT_JOIN);
		criteria.createAlias("field", "fie" , Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("sym.name", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') >= '" + dateStart + "' AND DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') <= '" + dateEnd +"'"));
		}
		criteria.addOrder(Order.asc("insertDate"));
		return criteria.list();
	}

	@Override
	public List<Quote> getGroupByInsertDate(String instrument, String dateStart, String dateEnd) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		criteria.createAlias("symbol", "sym" , Criteria.LEFT_JOIN);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.alias(Projections.sqlGroupProjection("CONVERT({alias}.insert_date, CHAR(19)) As d", "d", new String[] {"d"}, new Type[] {Hibernate.STRING}), "d"));
		criteria.add(Restrictions.eq("sym.name", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') >= '" + dateStart + "' AND DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') <= '" + dateEnd +"'"));
		}
		criteria.setProjection(projList);
		return criteria.list();
	}

	@Override
	public List<Quote> getNewestList() {
		// TODO Auto-generated method stub 
		criteria = createCriteria(Quote.class);
		criteria.setFetchMode("symbol", FetchMode.JOIN);
		criteria.setFetchMode("field", FetchMode.JOIN);
		return criteria.list();
	}

	@Override
	public Timestamp getTopInsertDateData(String symbolName) {
		// TODO Auto-generated method stub
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Quote.class);
		criteria.createAlias("symbol", "sym", Criteria.LEFT_JOIN);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.max("insertDate"));
		criteria.setProjection(projList);
		criteria.add(Restrictions.eq("sym.name", symbolName));
		return (Timestamp) criteria.uniqueResult();
	}

	@Override
	public List<Quote> getNewestListBy(String symbolName, Timestamp time) {
		// TODO Auto-generated method stub
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Quote.class);
		criteria.createAlias("symbol", "sym", Criteria.LEFT_JOIN);
		criteria.createAlias("field", "fie", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("sym.name", symbolName));
		criteria.add(Restrictions.eq("insertDate", time));
		return criteria.list();
	}

	@Override
	public Quote getNewestBySymbol(String name) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		criteria.createAlias("symbol", "sym", Criteria.LEFT_JOIN);
		criteria.add(Restrictions.eq("sym.name", name));
		criteria.addOrder(Order.desc("serverTime"));
		criteria.setMaxResults(1);
		List<Quote> list = criteria.list();
		if(list.size() != 0){
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void saveAll(List<Quote> list) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public Timestamp getTopServerDateData(Symbol symbol) {
		// TODO Auto-generated method stub
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Quote.class);
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.max("serverTime"));
		criteria.setProjection(projList);
		criteria.add(Restrictions.eq("symbol", symbol));
		return (Timestamp) criteria.uniqueResult();
		
	}

	@Override
	public List<Quote> getByInstrument2(String instrument, String dateStart,
			String dateEnd, int freq) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		Type[] types = {Hibernate.STRING, Hibernate.STRING};
		String[] dates = {dateStart, dateEnd};
		criteria.createAlias("symbol", "sym" , Criteria.LEFT_JOIN);
		criteria.createAlias("field", "fie" , Criteria.LEFT_JOIN);
		ProjectionList projList = Projections.projectionList();
		
		projList.add(Projections.property("quoteId"));
		projList.add(Projections.property("valNow"));
		projList.add(Projections.property("valLast"));
		projList.add(Projections.property("insertDate"));
		projList.add(Projections.property("serverTime"));
		projList.add(Projections.property("displayName"));
		projList.add(Projections.property("sym.symbolId"));
		projList.add(Projections.property("sym.name"));
		projList.add(Projections.property("fie.fieldId"));
		projList.add(Projections.property("fie.name"));
		
		projList.add(Projections.alias(Projections.sqlProjection("FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP({alias}.insert_date)/ "+freq+")*"+freq+") as timeNearTo", new String[] {"timeNearTo"}, new Type[] {Hibernate.STRING}), "timeNearTo"));
		criteria.add(Restrictions.eq("sym.name", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') >= ? AND DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') <= ?", dates, types));
		}
		criteria.setProjection(projList);
		criteria.addOrder(Order.asc("insertDate"));
		List<Quote> qList = checkResultData(criteria.list());
		return qList;
	}
	
	private List<Quote> checkResultData(List list) {
    	List<Quote> qList = new ArrayList<Quote>();
    	for(int i = 0; i < list.size(); i++) {
    		Quote quote = new Quote();
    		Object[] obj = (Object[]) list.get(i);
    		quote.setQuoteId(Integer.valueOf(obj[0].toString()));
    		quote.setValNow(Double.valueOf(obj[1].toString()));
    		quote.setValLast(Double.valueOf(obj[2].toString()));
    		quote.setInsertDate(Timestamp.valueOf(obj[10].toString()));
    		quote.setServerTime(Timestamp.valueOf(obj[4].toString()));
    		quote.setDisplayName(String.valueOf(obj[5]));
    		Symbol symbol = new Symbol();
    		symbol.setSymbolId(Integer.valueOf(obj[6].toString()));
    		symbol.setName(obj[7].toString());
    		Field field = new Field();
    		field.setFieldId(Integer.valueOf(obj[8].toString()));
    		field.setName(obj[9].toString());
    		quote.setField(field);
    		quote.setSymbol(symbol);
    		qList.add(quote);
    	}
    	return qList;
    }

	@Override
	public List<Quote> getByInstrument2(String instrument, String dateStart,
			String dateEnd, int freq, Timestamp time) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Quote.class);
		Type[] types = {Hibernate.STRING, Hibernate.STRING};
		String[] dates = {dateStart, dateEnd};
		criteria.createAlias("symbol", "sym" , Criteria.LEFT_JOIN);
		criteria.createAlias("field", "fie" , Criteria.LEFT_JOIN);
		ProjectionList projList = Projections.projectionList();
		
		projList.add(Projections.property("quoteId"));
		projList.add(Projections.property("valNow"));
		projList.add(Projections.property("valLast"));
		projList.add(Projections.property("insertDate"));
		projList.add(Projections.property("serverTime"));
		projList.add(Projections.property("displayName"));
		projList.add(Projections.property("sym.symbolId"));
		projList.add(Projections.property("sym.name"));
		projList.add(Projections.property("fie.fieldId"));
		projList.add(Projections.property("fie.name"));
		
		projList.add(Projections.alias(Projections.sqlProjection("FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP({alias}.insert_date)/ "+freq+")*"+freq+") as timeNearTo", new String[] {"timeNearTo"}, new Type[] {Hibernate.STRING}), "timeNearTo"));
		criteria.add(Restrictions.eq("sym.name", instrument));
		if(dateStart != null && !"".equals(dateStart) && dateEnd != null && !"".equals(dateEnd)) {
			criteria.add(Restrictions.sqlRestriction("DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') >= ? AND DATE_FORMAT({alias}.insert_date, '%Y-%m-%d') <= ?", dates, types));
		}
		criteria.add(Restrictions.eq("insertDate", time));
		criteria.setProjection(projList);
		criteria.addOrder(Order.asc("insertDate"));
		List<Quote> qList = checkResultData(criteria.list());
		return qList;
	}

}
