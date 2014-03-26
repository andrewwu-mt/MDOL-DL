package com.metrics.MDOL.dbo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * DfField entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Field implements java.io.Serializable {

	// Fields

	private Integer fieldId;
	private Symbol symbol;
	private String name;
	private String expression;
	private Timestamp insertDate;
	private Timestamp updateDate;
	
	@SuppressWarnings("rawtypes")
	private Set quotes = new HashSet(0);

	// Constructors

	/** default constructor */
	public Field() {
	}

	/** minimal constructor */
	public Field(Symbol symbol, String name, String expression,
			Timestamp insertDate, Timestamp updateDate) {
		this.symbol = symbol;
		this.name = name;
		this.expression = expression;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public Field(Symbol symbol, String name, String expression,
			Timestamp insertDate, Timestamp updateDate, Set quotes) {
		this.symbol = symbol;
		this.name = name;
		this.expression = expression;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
		this.quotes = quotes;
	}

	// Property accessors

	public Integer getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	@SuppressWarnings("rawtypes")
	public Set getQuotes() {
		return this.quotes;
	}

	@SuppressWarnings("rawtypes")
	public void setQuotes(Set quotes) {
		this.quotes = quotes;
	}

	/*public String getArgValue() {
		return argValue;
	}

	public void setArgValue(String argValue) {
		this.argValue = argValue;
	}
	
	public List<NowLast> getArgValueNowLast() {
		// value format example : arg1:JPY=,arg2:BID,arg3:1.05
		List<NowLast> list = new ArrayList<NowLast>();
		String[] args = this.argValue.split(",");
		for(int i = 0; i < args.length; i++) {
			String[] values = args[i].split(":");
			NowLast nowlast = new NowLast(values[0], values[1]);
			list.add(nowlast);
		}
		return list;
	}*/
}