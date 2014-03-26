package com.metrics.MDOL.dbo;

/**
 * MdolExchange entity. @author MyEclipse Persistence Tools
 */

public class Exchange implements java.io.Serializable {

	// Fields

	private Integer exchangeId;
	private String name;
	private String value;
	private String firstOpen;
	private String firstClose;
	private String secondOpen;
	private String secondClose;

	// Constructors

	/** default constructor */
	public Exchange() {
	}

	/** minimal constructor */
	public Exchange(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/** full constructor */
	public Exchange(String name, String value, String firstOpen,
			String firstClose, String secondOpen, String secondClose) {
		this.name = name;
		this.value = value;
		this.firstOpen = firstOpen;
		this.firstClose = firstClose;
		this.secondOpen = secondOpen;
		this.secondClose = secondClose;
	}

	// Property accessors

	public Integer getExchangeId() {
		return this.exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
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

	public String getFirstOpen() {
		return this.firstOpen;
	}

	public void setFirstOpen(String firstOpen) {
		this.firstOpen = firstOpen;
	}

	public String getFirstClose() {
		return this.firstClose;
	}

	public void setFirstClose(String firstClose) {
		this.firstClose = firstClose;
	}

	public String getSecondOpen() {
		return this.secondOpen;
	}

	public void setSecondOpen(String secondOpen) {
		this.secondOpen = secondOpen;
	}

	public String getSecondClose() {
		return this.secondClose;
	}

	public void setSecondClose(String secondClose) {
		this.secondClose = secondClose;
	}

}