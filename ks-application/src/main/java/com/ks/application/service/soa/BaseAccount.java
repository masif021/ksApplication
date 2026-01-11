package com.ks.application.service.soa;

import java.math.BigDecimal;

public class BaseAccount {

	private String clientName;
    private String accountTitle;
    private String accountNo;
    private String branch;
    private String region;
    private String fromDate;
    private String toDate;
    private String product;
    private String iban;
    private String currency;
    private String address;
    private String barCode;
    private String category;
    
    private String pageNo;
    private String totalPages;
    
    private BigDecimal creditTrxCount;
    private BigDecimal creditTrxAmount;
    private BigDecimal debitTrxCount;
    private BigDecimal debitTrxAmount;
    
	public String getAccountTitle() {
		return accountTitle;
	}
	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}
	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
	/**
	 * @return the creditTrxCount
	 */
	public BigDecimal getCreditTrxCount() {
		return creditTrxCount;
	}
	/**
	 * @param creditTrxCount the creditTrxCount to set
	 */
	public void setCreditTrxCount(BigDecimal creditTrxCount) {
		this.creditTrxCount = creditTrxCount;
	}
	/**
	 * @return the creditTrxAmount
	 */
	public BigDecimal getCreditTrxAmount() {
		return creditTrxAmount;
	}
	/**
	 * @param creditTrxAmount the creditTrxAmount to set
	 */
	public void setCreditTrxAmount(BigDecimal creditTrxAmount) {
		this.creditTrxAmount = creditTrxAmount;
	}
	/**
	 * @return the debitTrxCount
	 */
	public BigDecimal getDebitTrxCount() {
		return debitTrxCount;
	}
	/**
	 * @param debitTrxCount the debitTrxCount to set
	 */
	public void setDebitTrxCount(BigDecimal debitTrxCount) {
		this.debitTrxCount = debitTrxCount;
	}
	/**
	 * @return the debitTrxAmount
	 */
	public BigDecimal getDebitTrxAmount() {
		return debitTrxAmount;
	}
	/**
	 * @param debitTrxAmount the debitTrxAmount to set
	 */
	public void setDebitTrxAmount(BigDecimal debitTrxAmount) {
		this.debitTrxAmount = debitTrxAmount;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
    

}
