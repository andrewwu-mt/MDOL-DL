package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * DfDef entity. @author MyEclipse Persistence Tools
 */

public class Def implements java.io.Serializable {

	// Fields

	private Integer defId;
	private String name;
	private String val;
	private String description;
	private Timestamp insertDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public Def() {
	}

	/** minimal constructor */
	public Def(String name, String val, Timestamp insertDate,
			Timestamp updateDate) {
		this.name = name;
		this.val = val;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
	}

	/** full constructor */
	public Def(String name, String val, String description, Timestamp insertDate,
			Timestamp updateDate) {
		this.name = name;
		this.val = val;
		this.description = description;
		this.insertDate = insertDate;
		this.updateDate = updateDate;
	}

	// Property accessors

	public Integer getDefId() {
		return this.defId;
	}

	public void setDefId(Integer defId) {
		this.defId = defId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public String getDefaultVal() {
		if(this.val.equals("null")) {
			return null;
		} else {
			return this.val;
		}
	}

}