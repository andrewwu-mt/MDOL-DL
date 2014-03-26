package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfLogType entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class LogType implements java.io.Serializable {

	// Fields

	private Integer logTypeId;
	private String name;
	@SuppressWarnings("rawtypes")
	private Set logs = new HashSet(0);

	// Constructors

	/** default constructor */
	public LogType() {
	}

	/** minimal constructor */
	public LogType(String name) {
		this.name = name;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public LogType(String name, Set logs) {
		this.name = name;
		this.logs = logs;
	}

	// Property accessors

	public Integer getLogTypeId() {
		return this.logTypeId;
	}

	public void setLogTypeId(Integer logTypeId) {
		this.logTypeId = logTypeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@SuppressWarnings("rawtypes")
	public Set getLogs() {
		return this.logs;
	}
	@SuppressWarnings("rawtypes")
	public void setLogs(Set logs) {
		this.logs = logs;
	}

}