package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfMailType entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class MailType implements java.io.Serializable {

	// Fields

	private Integer mailTypeId;
	private String name;
	private Set mails = new HashSet(0);

	// Constructors

	/** default constructor */
	public MailType() {
	}

	/** minimal constructor */
	public MailType(String name) {
		this.name = name;
	}

	/** full constructor */
	public MailType(String name, Set mails) {
		this.name = name;
		this.mails = mails;
	}

	// Property accessors

	public Integer getMailTypeId() {
		return this.mailTypeId;
	}

	public void setMailTypeId(Integer mailTypeId) {
		this.mailTypeId = mailTypeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getMails() {
		return this.mails;
	}

	public void setMails(Set mails) {
		this.mails = mails;
	}

}