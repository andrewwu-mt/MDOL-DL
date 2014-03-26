package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfSymbol entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class Symbol implements java.io.Serializable {

	// Fields

	private Integer symbolId;
	private Template template;
	private String name;
	private Integer link;
	private Integer active;
	private Exchange exchange;
	private Currency currency;
	private Set quotes = new HashSet(0);
	private Set fields = new HashSet(0);

	// Constructors

	/** default constructor */
	public Symbol() {
	}

	/** minimal constructor */
	public Symbol(Template template, String name, Integer link, Integer active, Exchange exchange, Currency currency) {
		this.template = template;
		this.name = name;
		this.link = link;
		this.active = active;
		this.exchange = exchange;
		this.currency = currency;
	}

	/** full constructor */
	public Symbol(Template template, String name, Integer link, Integer active,
			Set quotes, Set fields, Exchange exchange, Currency currency) {
		this.template = template;
		this.name = name;
		this.link = link;
		this.active = active;
		this.exchange = exchange;
		this.currency = currency;
		this.quotes = quotes;
		this.fields = fields;
	}

	// Property accessors

	public Integer getSymbolId() {
		return this.symbolId;
	}

	public void setSymbolId(Integer symbolId) {
		this.symbolId = symbolId;
	}

	public Template getTemplate() {
		return this.template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLink() {
		return this.link;
	}

	public void setLink(Integer link) {
		this.link = link;
	}

	public Set getQuotes() {
		return this.quotes;
	}

	public void setQuotes(Set quotes) {
		this.quotes = quotes;
	}

	public Set getFields() {
		return this.fields;
	}

	public void setFields(Set fields) {
		this.fields = fields;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}