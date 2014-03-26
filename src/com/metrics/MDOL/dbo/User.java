package com.metrics.MDOL.dbo;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class User  implements java.io.Serializable {


    // Fields    

     private Integer userId;
     private UserSec userSec;
     private String name;
     private String email;
     private String password;
     private String phone;
     private String mobile;
     private Integer admin;
     private Integer active;
     private Timestamp insertDate;
     private Timestamp updateDate;
     private Set logs = new HashSet(0);


    // Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(UserSec userSec, String name, String email, String password, String phone, String mobile, Integer admin, Integer active, Timestamp insertDate, Timestamp updateDate) {
        this.userSec = userSec;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.mobile = mobile;
        this.admin = admin;
        this.active = active;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
    }
    
    /** full constructor */
    public User(UserSec userSec, String name, String email, String password, String phone, String mobile, Integer admin, Integer active, Timestamp insertDate, Timestamp updateDate, Set logs) {
        this.userSec = userSec;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.mobile = mobile;
        this.admin = admin;
        this.active = active;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
        this.logs = logs;
    }

   
    // Property accessors

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
    public UserSec getUserSec() {
		return userSec;
	}

	public void setUserSec(UserSec userSec) {
		this.userSec = userSec;
	}

	public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAdmin() {
        return this.admin;
    }
    
    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getActive() {
        return this.active;
    }
    
    public void setActive(Integer active) {
        this.active = active;
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

    public Set getLogs() {
        return this.logs;
    }
    
    public void setLogs(Set logs) {
        this.logs = logs;
    }
   
    public String getUserAdmin(){
    	if(this.admin == 1){
    		return "Administrator";
    	} else {
    		return "User";
    	}
    }







}