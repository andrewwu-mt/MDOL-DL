package com.metrics.MDOL.dbo;

//default package



/**
* Item entity. @author MyEclipse Persistence Tools
*/
@SuppressWarnings("serial")
public class Item  implements java.io.Serializable {


 // Fields    

  private Integer itemId;
  private String name;
  private Integer active;


 // Constructors

 /** default constructor */
 public Item() {
 }

 
 /** full constructor */
 public Item(String name, Integer active) {
     this.name = name;
     this.active = active;
 }


 // Property accessors

 public Integer getItemId() {
     return this.itemId;
 }
 
 public void setItemId(Integer itemId) {
     this.itemId = itemId;
 }

 public String getName() {
     return this.name;
 }
 
 public void setName(String name) {
     this.name = name;
 }

 public Integer getActive() {
     return this.active;
 }
 
 public void setActive(Integer active) {
     this.active = active;
 }









}