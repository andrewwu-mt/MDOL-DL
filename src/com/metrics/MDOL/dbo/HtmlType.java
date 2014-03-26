package com.metrics.MDOL.dbo;
// default package

import java.util.HashSet;
import java.util.Set;


/**
 * HtmlType entity. @author MyEclipse Persistence Tools
 */

public class HtmlType  implements java.io.Serializable {


    // Fields    

     private Integer htmlTypeId;
     private String name;
     private Set htmls = new HashSet(0);


    // Constructors

    /** default constructor */
    public HtmlType() {
    }

	/** minimal constructor */
    public HtmlType(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public HtmlType(String name, Set htmls) {
        this.name = name;
        this.htmls = htmls;
    }

   
    // Property accessors

    public Integer getHtmlTypeId() {
        return this.htmlTypeId;
    }
    
    public void setHtmlTypeId(Integer htmlTypeId) {
        this.htmlTypeId = htmlTypeId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Set getHtmls() {
        return this.htmls;
    }
    
    public void setHtmls(Set htmls) {
        this.htmls = htmls;
    }
   








}