package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * DfStory entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Story implements java.io.Serializable {

	// Fields

	private Integer storyId;
	private String headline;
	private String content;
	private Timestamp time;
	private String code;
	private String lang;
	private Timestamp insertDate;
	private String companyCode;

	// Constructors

	/** default constructor */
	public Story() {
	}

	/** full constructor */
	public Story(String headline, String content, Timestamp time,
			String code, String lang, Timestamp insertDate, String companyCode) {
		this.headline = headline;
		this.content = content;
		this.time = time;
		this.code = code;
		this.lang = lang;
		this.insertDate = insertDate;
		this.companyCode = companyCode;
	}

	// Property accessors

	public Integer getStoryId() {
		return this.storyId;
	}

	public void setStoryId(Integer storyId) {
		this.storyId = storyId;
	}

	public String getHeadline() {
		return this.headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}