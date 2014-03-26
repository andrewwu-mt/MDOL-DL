package com.metrics.MDOL.dbo;
// default package



/**
 * FieldList entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class FieldList  implements java.io.Serializable {


    // Fields    

     private Integer fieldListId;
     private String name;


    // Constructors

    /** default constructor */
    public FieldList() {
    }

    
    /** full constructor */
    public FieldList(String name) {
        this.name = name;
    }

   
    // Property accessors

    public Integer getFieldListId() {
        return this.fieldListId;
    }
    
    public void setFieldListId(Integer fieldListId) {
        this.fieldListId = fieldListId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   








}