package com.metrics.MDOL.dbo;

import java.sql.Timestamp;

// default package



/**
 * Ts entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Ts  implements java.io.Serializable {


    // Fields    

     private Integer tsId;
     private String ts;
     private String bid;
     private String open;
     private String high;
     private String low;
     private String ask;
     private String highBid;
     private String lowBid;
     private String openAsk;
     private String highAsk;
     private String lowAsk;
     private String close;
     private String vol;
     private String last;
     private Timestamp date;
     private String displayName;


    // Constructors

    /** default constructor */
    public Ts() {
    }

    
    /** full constructor */
    public Ts(String ts, String bid, String open, String high, String low, String ask, String highBid, String lowBid, String openAsk, String highAsk, String lowAsk, String close, String vol, Timestamp date, String displayName) {
        this.ts = ts;
        this.bid = bid;
        this.open = open;
        this.high = high;
        this.low = low;
        this.ask = ask;
        this.highBid = highBid;
        this.lowBid = lowBid;
        this.openAsk = openAsk;
        this.highAsk = highAsk;
        this.lowAsk = lowAsk;
        this.close = close;
        this.vol = vol;
        this.date = date;
        this.displayName = displayName;
    }

   
    // Property accessors

    public Integer getTsId() {
        return this.tsId;
    }
    
    public void setTsId(Integer tsId) {
        this.tsId = tsId;
    }

    public String getTs() {
        return this.ts;
    }
    
    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBid() {
        return this.bid;
    }
    
    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getOpen() {
        return this.open;
    }
    
    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return this.high;
    }
    
    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return this.low;
    }
    
    public void setLow(String low) {
        this.low = low;
    }

    public String getAsk() {
        return this.ask;
    }
    
    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getHighBid() {
        return this.highBid;
    }
    
    public void setHighBid(String highBid) {
        this.highBid = highBid;
    }

    public String getLowBid() {
        return this.lowBid;
    }
    
    public void setLowBid(String lowBid) {
        this.lowBid = lowBid;
    }

    public String getOpenAsk() {
        return this.openAsk;
    }
    
    public void setOpenAsk(String openAsk) {
        this.openAsk = openAsk;
    }

    public String getHighAsk() {
        return this.highAsk;
    }
    
    public void setHighAsk(String highAsk) {
        this.highAsk = highAsk;
    }

    public String getLowAsk() {
        return this.lowAsk;
    }
    
    public void setLowAsk(String lowAsk) {
        this.lowAsk = lowAsk;
    }

    public Timestamp getDate() {
        return this.date;
    }
    
    public void setDate(Timestamp date) {
        this.date = date;
    }


	public String getClose() {
		return close;
	}


	public void setClose(String close) {
		this.close = close;
	}


	public String getVol() {
		return vol;
	}


	public void setVol(String vol) {
		this.vol = vol;
	}


	public String getLast() {
		return last;
	}


	public void setLast(String last) {
		this.last = last;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}







}