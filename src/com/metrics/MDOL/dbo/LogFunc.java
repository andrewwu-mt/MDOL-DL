package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * DfLogFunc entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")

public class LogFunc implements java.io.Serializable {

	// Fields

	private Integer logFuncId;
	private String name;
	@SuppressWarnings("rawtypes")
	private Set logs = new HashSet(0);

	// Constructors

	/** default constructor */
	public LogFunc() {
	}

	/** minimal constructor */
	public LogFunc(String name) {
		this.name = name;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public LogFunc(String name, Set logs) {
		this.name = name;
		this.logs = logs;
	}

	// Property accessors

	public Integer getLogFuncId() {
		return this.logFuncId;
	}

	public void setLogFuncId(Integer logFuncId) {
		this.logFuncId = logFuncId;
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