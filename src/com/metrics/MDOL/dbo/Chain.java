package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfChain entity. @author MyEclipse Persistence Tools
 */

public class Chain implements java.io.Serializable {

	// Fields

	private Integer chainId;
	private String name;
	private Set links = new HashSet(0);

	// Constructors

	/** default constructor */
	public Chain() {
	}

	/** minimal constructor */
	public Chain(String name) {
		this.name = name;
	}

	/** full constructor */
	public Chain(String name, Set links) {
		this.name = name;
		this.links = links;
	}

	// Property accessors

	public Integer getChainId() {
		return this.chainId;
	}

	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getLinks() {
		return this.links;
	}

	public void setLinks(Set links) {
		this.links = links;
	}

}