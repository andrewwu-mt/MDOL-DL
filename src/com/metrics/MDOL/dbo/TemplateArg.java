package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * templateArg entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TemplateArg implements java.io.Serializable {

	// Fields

	private Integer templateArgId;
	private Template template;
	private String field;
	private String expression;
	private Timestamp insertDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public TemplateArg() {
	}

	/** full constructor */
	public TemplateArg(Template template, String field,
			String expression, Timestamp insertDate, Timestamp updateDate) {
		this.template = template;
		this.field = field;
		this.expression = expression;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
	}

	// Property accessors

	public Integer getTemplateArgId() {
		return this.templateArgId;
	}

	public void setTemplateArgId(Integer templateArgId) {
		this.templateArgId = templateArgId;
	}

	public Template getTemplate() {
		return this.template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
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

}