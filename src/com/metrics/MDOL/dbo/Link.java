package com.metrics.MDOL.dbo;

/**
 * DfLong entity. @author MyEclipse Persistence Tools
 */

public class Link implements java.io.Serializable {

	// Fields

	private Integer linkId;
	private Chain chain;
	private String name;

	// Constructors

	/** default constructor */
	public Link() {
	}

	/** full constructor */
	public Link(Chain chain, String name) {
		this.chain = chain;
		this.name = name;
	}

	// Property accessors

	public Integer getLinkId() {
		return this.linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Chain getChain() {
		return chain;
	}

	public void setChain(Chain chain) {
		this.chain = chain;
	}

	
}