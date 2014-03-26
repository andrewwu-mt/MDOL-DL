package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * DfQuote entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Quote implements java.io.Serializable {

	// Fields

	private Integer quoteId;
	private Symbol symbol;
	private Field field;
	private Double valNow;
	private Double valLast;
	private String displayName;
	private Timestamp insertDate;
	private Timestamp serverTime;
	
	// Constructors

	/** default constructor */
	public Quote() {
	}

	/** full constructor */
	public Quote(Symbol symbol, Field field, Double valNow,
			Double valLast, Timestamp insertDate, Timestamp serverTime, String displayName) {
		this.symbol = symbol;
		this.field = field;
		this.valNow = valNow;
		this.valLast = valLast;
		this.insertDate = insertDate;
		this.serverTime = serverTime;
		this.displayName = displayName;
	}

	// Property accessors

	public Integer getQuoteId() {
		return this.quoteId;
	}

	public void setQuoteId(Integer quoteId) {
		this.quoteId = quoteId;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Double getValNow() {
		return this.valNow;
	}

	public void setValNow(Double valNow) {
		this.valNow = valNow;
	}

	public Double getValLast() {
		return this.valLast;
	}

	public void setValLast(Double valLast) {
		this.valLast = valLast;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getServerTime() {
		return serverTime;
	}

	public void setServerTime(Timestamp serverTime) {
		this.serverTime = serverTime;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}