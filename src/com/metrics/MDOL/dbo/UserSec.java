package com.metrics.MDOL.dbo;

import java.util.HashSet;
import java.util.Set;

/**
 * usersec entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class UserSec implements java.io.Serializable {

	// Fields

	private Integer userSecId;
	private String name;
	private Set users = new HashSet(0);

	// Constructors

	/** default constructor */
	public UserSec() {
	}

	/** minimal constructor */
	public UserSec(String name) {
		this.name = name;
	}

	/** full constructor */
	public UserSec(String name, Set users) {
		this.name = name;
		this.users = users;
	}

	// Property accessors

	public Integer getUserSecId() {
		return this.userSecId;
	}

	public void setUserSecId(Integer userSecId) {
		this.userSecId = userSecId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

}