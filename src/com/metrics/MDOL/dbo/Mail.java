package com.metrics.MDOL.dbo;

/**
 * DfMail entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Mail implements java.io.Serializable {

	// Fields

	private Integer mailId;
	private MailType mailType;
	private String title;
	private String content;
	private String addr;
	private Integer active;
	private Integer freq;

	// Constructors

	/** default constructor */
	public Mail() {
	}

	/** full constructor */
	public Mail(MailType mailType, String title, String content,
			String addr, Integer active, Integer freq) {
		this.mailType = mailType;
		this.title = title;
		this.content = content;
		this.addr = addr;
		this.active = active;
		this.freq = freq;
	}

	// Property accessors

	public Integer getMailId() {
		return this.mailId;
	}

	public void setMailId(Integer mailId) {
		this.mailId = mailId;
	}

	public MailType getMailType() {
		return this.mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}
}