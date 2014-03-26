package com.metrics.MDOL.dbo;

/**
 * DfArgList entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class ArgList implements java.io.Serializable {

	// Fields

	private Integer argListId;
	private Template template;
	private String name;
	private String example;

	// Constructors

	/** default constructor */
	public ArgList() {
	}

	/** full constructor */
	public ArgList(Integer argListId, Template template, String name,
			String example) {
		this.argListId = argListId;
		this.template = template;
		this.name = name;
		this.example = example;
	}

	// Property accessors

	public Integer getArgListId() {
		return this.argListId;
	}

	public void setArgListId(Integer argListId) {
		this.argListId = argListId;
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

	public String getExample() {
		return this.example;
	}

	public void setExample(String example) {
		this.example = example;
	}

}