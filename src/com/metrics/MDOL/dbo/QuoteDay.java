package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * MdolQuoteDay entity. @author MyEclipse Persistence Tools
 */

public class QuoteDay implements java.io.Serializable {

	// Fields

	private Integer quoteDayId;
	private Symbol symbol;
	private Field field;
	private Double valNow;
	private Double valLast;
	private Timestamp insertDate;
	private Timestamp serverTime;
	private String displayName;

	// Constructors

	/** default constructor */
	public QuoteDay() {
	}

	/** full constructor */
	public QuoteDay(Symbol symbol, Field field,
			Double valNow, Double valLast, Timestamp insertDate,
			Timestamp serverTime, String displayName) {
		this.symbol = symbol;
		this.field = field;
		this.valNow = valNow;
		this.valLast = valLast;
		this.insertDate = insertDate;
		this.serverTime = serverTime;
		this.displayName = displayName;
	}

	// Property accessors

	public Integer getQuoteDayId() {
		return this.quoteDayId;
	}

	public void setQuoteDayId(Integer quoteDayId) {
		this.quoteDayId = quoteDayId;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Field getField() {
		return field;
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
		return this.serverTime;
	}

	public void setServerTime(Timestamp serverTime) {
		this.serverTime = serverTime;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}