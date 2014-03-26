package com.metrics.MDOL.dbo;

import java.sql.Timestamp;
// default package



/**
 * PageUpdate entity. @author MyEclipse Persistence Tools
 */

public class PageUpdate  implements java.io.Serializable {


    // Fields    

     private Integer pageUpdateId;
     private Integer row;
     private String value;
     private Timestamp insertDate;


    // Constructors

    /** default constructor */
    public PageUpdate() {
    }

    
    /** full constructor */
    public PageUpdate(Integer row, String value, Timestamp insertDate) {
        this.row = row;
        this.value = value;
        this.insertDate = insertDate;
    }

   
    // Property accessors

    public Integer getPageUpdateId() {
        return this.pageUpdateId;
    }
    
    public void setPageUpdateId(Integer pageUpdateId) {
        this.pageUpdateId = pageUpdateId;
    }

    public Integer getRow() {
        return this.row;
    }
    
    public void setRow(Integer row) {
        this.row = row;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }


	public Timestamp getInsertDate() {
		return insertDate;
	}


	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
   








}