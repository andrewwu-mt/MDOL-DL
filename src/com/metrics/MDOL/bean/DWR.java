package com.metrics.MDOL.bean;

import com.metrics.MDOL.dao.FieldDao;
import com.metrics.MDOL.dao.ItemDao;
import com.metrics.MDOL.dao.QuoteDao;
import com.metrics.MDOL.dao.StoryDao;
import com.metrics.MDOL.dao.SymbolDao;
import com.metrics.MDOL.dao.TsDao;

public class DWR {

	public SymbolDao symbolDao;
	public FieldDao fieldDao;
	public QuoteDao quoteDao;
	public StoryDao storyDao;
	public ItemDao itemDao;
	public TsDao tsDao;
		
	
	public ItemDao getItemDao() {
		return itemDao;
	}
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	public SymbolDao getSymbolDao() {
		return symbolDao;
	}
	public void setSymbolDao(SymbolDao symbolDao) {
		this.symbolDao = symbolDao;
	}
	public FieldDao getFieldDao() {
		return fieldDao;
	}
	public void setFieldDao(FieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}
	public QuoteDao getQuoteDao() {
		return quoteDao;
	}
	public void setQuoteDao(QuoteDao quoteDao) {
		this.quoteDao = quoteDao;
	}
	public StoryDao getStoryDao() {
		return storyDao;
	}
	public void setStoryDao(StoryDao storyDao) {
		this.storyDao = storyDao;
	}
	public TsDao getTsDao() {
		return tsDao;
	}
	public void setTsDao(TsDao tsDao) {
		this.tsDao = tsDao;
	}
	
	
	
	
	
}
