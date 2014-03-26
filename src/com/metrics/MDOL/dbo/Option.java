package com.metrics.MDOL.dbo;

/**
 * DfOption entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Option implements java.io.Serializable {

	// Fields

	private Integer optionId;
	private Integer freq;
	private String email;
	private String perName;
	private Integer port;
	private String host;
	private String password;
	private Integer newsLang;
	private Integer active;
	private String serviceName;
	private String sessionName;
	private String userName;
	private String enumType;

	// Constructors

	/** default constructor */
	public Option() {
	}

	/** minimal constructor */
	public Option(Integer freq, String email, Integer port, String host,
			String password, Integer newsLang, Integer active) {
		this.freq = freq;
		this.email = email;
		this.port = port;
		this.host = host;
		this.password = password;
		this.newsLang = newsLang;
		this.active = active;
	}

	/** full constructor */
	public Option(Integer freq, String email, String perName, Integer port,
			String host, String password, Integer newsLang, Integer active,
			String serviceName, String sessionName, String userName,
			String enumType) {
		this.freq = freq;
		this.email = email;
		this.perName = perName;
		this.port = port;
		this.host = host;
		this.password = password;
		this.newsLang = newsLang;
		this.active = active;
		this.serviceName = serviceName;
		this.sessionName = sessionName;
		this.userName = userName;
		this.enumType = enumType;
	}

	// Property accessors

	public Integer getOptionId() {
		return this.optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getFreq() {
		return this.freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPerName() {
		return this.perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getNewsLang() {
		return this.newsLang;
	}

	public void setNewsLang(Integer newsLang) {
		this.newsLang = newsLang;
	}

	public Integer getActive() {
		return this.active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}
}