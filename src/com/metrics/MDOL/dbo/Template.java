package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfTemplate entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class Template implements java.io.Serializable {

	// Fields

	private Integer templateId;
	private String name;
	private Set symbols = new HashSet(0);
	private Set templateArgs = new HashSet(0);

	// Constructors

	/** default constructor */
	public Template() {
	}

	/** minimal constructor */
	public Template(String name) {
		this.name = name;
	}

	/** full constructor */
	public Template(String name, Set symbols, Set templateArgs) {
		this.name = name;
		this.symbols = symbols;
		this.templateArgs = templateArgs;
	}

	// Property accessors

	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getSymbols() {
		return this.symbols;
	}

	public void setSymbols(Set symbols) {
		this.symbols = symbols;
	}

	public Set getTemplateArgs() {
		return this.templateArgs;
	}

	public void setTemplateArgs(Set templateArgs) {
		this.templateArgs = templateArgs;
	}

}