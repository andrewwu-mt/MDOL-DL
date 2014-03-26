package com.metrics.MDOL.dbo;

/**
 * DfSymbolArg entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SymbolArg implements java.io.Serializable {

	// Fields

	private Integer symbolArgId;
	private Symbol symbol;
	private String name;
	private String val;

	// Constructors

	/** default constructor */
	public SymbolArg() {
	}

	/** full constructor */
	public SymbolArg(Symbol symbol, String name, String val) {
		this.symbol = symbol;
		this.name = name;
		this.val = val;
	}

	// Property accessors

	public Integer getSymbolArgId() {
		return this.symbolArgId;
	}

	public void setSymbolArgId(Integer symbolArgId) {
		this.symbolArgId = symbolArgId;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}