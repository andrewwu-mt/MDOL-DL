package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

/**
 * DfLog entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Log implements java.io.Serializable {

	// Fields

	private Integer logId;
	private LogFunc logFunc;
	private LogType logType;
	private User user;
	private Timestamp insertDate;
	private String remark;
	private String sentence;
	private String interval;
	private String date;
	private String time;

	// Constructors

	/** default constructor */
	public Log() {
	}

	/** full constructor */
	public Log(LogFunc logFunc, LogType logType, User user,
			Timestamp insertDate, String remark) {
		this.logFunc = logFunc;
		this.logType = logType;
		this.user = user;
		this.insertDate = insertDate;
		this.remark = remark;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public LogFunc getLogFunc() {
		return this.logFunc;
	}

	public void setLogFunc(LogFunc logFunc) {
		this.logFunc = logFunc;
	}

	public LogType getLogType() {
		return this.logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	

}