package com.metrics.MDOL.dbo;

/**
 * MdolCurrency entity. @author MyEclipse Persistence Tools
 */

public class Currency implements java.io.Serializable {

	// Fields

	private Integer currencyId;
	private String name;
	private String value;

	// Constructors

	/** default constructor */
	public Currency() {
	}

	/** full constructor */
	public Currency(String name, String value) {
		this.name = name;
		this.value = value;
	}

	// Property accessors

	public Integer getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}