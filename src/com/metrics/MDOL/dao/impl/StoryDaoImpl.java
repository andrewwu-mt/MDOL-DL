package com.metrics.MDOL.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.metrics.MDOL.base.BaseHibernateDaoSupport;
import com.metrics.MDOL.dao.StoryDao;
import com.metrics.MDOL.dbo.Story;
import com.metrics.MDOL.dbo.Symbol;

@SuppressWarnings("unchecked")
public class StoryDaoImpl extends BaseHibernateDaoSupport implements StoryDao {

	@Override
	public void save(Story obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(obj);
	}

	@Override
	public void update(Story obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(obj);
	}

	@Override
	public void delete(Story obj) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(obj);
	}

	@Override
	public List<Story> getBy(String headline, Timestamp time) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Story.class);
		criteria.add(Restrictions.and(Restrictions.eq("headline", headline), Restrictions.eq("time", time)));
		return criteria.list();
	}

	@Override
	public List<Story> betBy() {
		// TODO Auto-generated method stub
		criteria = createCriteria(Story.class);
		return criteria.list();
	}

	@Override
	public Story getBy(int id) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Story.class);
		criteria.add(Restrictions.eq("storyId", id));
		List<Story> list = criteria.list();
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Story> getBySize(int size) {
		// TODO Auto-generated method stub
		criteria = createCriteria(Story.class);
		criteria.addOrder(Order.desc("insertDate"));
		criteria.setMaxResults(size);
		return criteria.list();
	}

	@Override
	public List<Story> getBySizeAndCode(int size, String code) {
		// TODO Auto-generated method stub
		/*String[] codes = code.split(",");
		String exp = "";
		for(int i=0 ; i<codes.length ; i++){
			String codesSingle = codes[i];
			exp = exp + "code like '%" + codesSingle + "%'";
			if(i != codes.length -1){
				exp = exp + " or ";
			}
		}
		String sqlQuery = "from Story where "+exp;
		List<Story> list =  this.getHibernateTemplate().find(sqlQuery);
		return list;*/
		
		/*String[] codes = code.split(",");
		for(int i=0 ; i<codes.length ; i++){
			String codeSingle = codes[i];
			criteria.add(Restrictions.like("code", "%"+codeSingle+"%"));
		}*/

		
		criteria = createCriteria(Story.class);
		criteria.add(Restrictions.like("code", "%"+code+"%"));
		criteria.addOrder(Order.desc("insertDate"));
		criteria.setMaxResults(size);
		return criteria.list();
	}

	@Override
	public List<Story> getBySizeAndCompany(int size, String company) {
		// TODO Auto-generated method stub
		//criteria = createCriteria(Story.class);
		String[] comps = company.split(",");
		String exp = "";
		for(int i=0 ; i<comps.length ; i++){
			String compSingle = comps[i];
			exp = exp + "company_code like '%" + compSingle + "%'";
			if(i != comps.length -1){
				exp = exp + " or ";
			}
		}
		String sqlQuery = "from Story where "+exp;
		List<Story> list =  this.getHibernateTemplate().find(sqlQuery);
		return list;
		
		//criteria.add(Restrictions.in("companyCode", comps));
		//criteria.add(Restrictions.like("companyCode", "%"+company+"%"));
		//criteria.addOrder(Order.desc("insertDate"));
		//criteria.setMaxResults(size);
		//return criteria.list();
	}

	@Override
	public List<Story> getBySizeAndCodeAndCompany(int size, String code,
			String company) {
		// TODO Auto-generated method stub
		/*criteria = createCriteria(Story.class);
		criteria.add(Restrictions.like("code", "%"+code+"%"));
		List<Story> list = criteria.list();*/
		
		String[] comps = company.split(",");
		String exp = "";
		for(int i=0 ; i<comps.length ; i++){
			String compSingle = comps[i];
			exp = exp + "company_code like '%" + compSingle + "%'";
			if(i != comps.length -1){
				exp = exp + " or ";
			}
		}
		String sqlQuery = "from Story where code like '%"+code+"%' and ("+exp+")";
		List<Story> list =  this.getHibernateTemplate().find(sqlQuery);
		return list;
		
		/*criteria.add(Restrictions.like("companyCode", "%"+company+"%"));
		criteria.addOrder(Order.desc("insertDate"));
		criteria.setMaxResults(size);
		return criteria.list();*/
	}
	
}
