package com.metrics.MDOL.dbo;
// default package

import java.sql.Timestamp;


/**
 * Html entity. @author MyEclipse Persistence Tools
 */

public class Html  implements java.io.Serializable {


    // Fields    

     private Integer htmlId;
     private HtmlType htmlType;
     private String name;
     private Timestamp insertDate;
     private Integer defMark;


    // Constructors

    /** default constructor */
    public Html() {
    }

    
    /** full constructor */
    public Html(HtmlType htmlType, String name, Timestamp insertDate, Integer defMark) {
        this.htmlType = htmlType;
        this.name = name;
        this.insertDate = insertDate;
        this.defMark = defMark;
    }

   
    // Property accessors

    public Integer getHtmlId() {
        return this.htmlId;
    }
    
    public void setHtmlId(Integer htmlId) {
        this.htmlId = htmlId;
    }

    public HtmlType getHtmlType() {
        return this.htmlType;
    }
    
    public void setHtmlType(HtmlType htmlType) {
        this.htmlType = htmlType;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getInsertDate() {
        return this.insertDate;
    }
    
    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

	public Integer getDefMark() {
		return defMark;
	}

	public void setDefMark(Integer defMark) {
		this.defMark = defMark;
	}
}