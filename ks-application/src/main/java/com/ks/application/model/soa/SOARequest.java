package com.ks.application.model.soa;

import java.math.BigDecimal;

public class SOARequest {
    
	private String inputFolder;
    private String outputFolder;
    private String barCodePrefix;
    private BigDecimal barCodeStartingNumber;
    private BigDecimal barCodeEndingNumber;
    private BigDecimal barCodeLength;
    private String category;
    private String statusDesc;
    public SOARequest() {}

	/**
	 * @return the inputFolder
	 */
	public String getInputFolder() {
		return inputFolder;
	}

	/**
	 * @param inputFolder the inputFolder to set
	 */
	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	/**
	 * @return the outputFolder
	 */
	public String getOutputFolder() {
		return outputFolder;
	}

	/**
	 * @param outputFolder the outputFolder to set
	 */
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}

	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	/**
	 * @return the barCodePrefix
	 */
	public String getBarCodePrefix() {
		return barCodePrefix;
	}

	/**
	 * @param barCodePrefix the barCodePrefix to set
	 */
	public void setBarCodePrefix(String barCodePrefix) {
		this.barCodePrefix = barCodePrefix;
	}

	/**
	 * @return the barCodeStartingNumber
	 */
	public BigDecimal getBarCodeStartingNumber() {
		return barCodeStartingNumber;
	}

	/**
	 * @param barCodeStartingNumber the barCodeStartingNumber to set
	 */
	public void setBarCodeStartingNumber(BigDecimal barCodeStartingNumber) {
		this.barCodeStartingNumber = barCodeStartingNumber;
	}

	/**
	 * @return the barCodeEndingNumber
	 */
	public BigDecimal getBarCodeEndingNumber() {
		return barCodeEndingNumber;
	}

	/**
	 * @param barCodeEndingNumber the barCodeEndingNumber to set
	 */
	public void setBarCodeEndingNumber(BigDecimal barCodeEndingNumber) {
		this.barCodeEndingNumber = barCodeEndingNumber;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getBarCodeLength() {
		return barCodeLength;
	}

	public void setBarCodeLength(BigDecimal barCodeLength) {
		this.barCodeLength = barCodeLength;
	}
	
    
}
