package com.ks.application.model.soa;

import java.math.BigDecimal;

public class TransactionRow {

    private String date;
    private String particulars;
    private String instrumentNo;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance;
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the particulars
	 */
	public String getParticulars() {
		return particulars;
	}
	/**
	 * @param particulars the particulars to set
	 */
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	/**
	 * @return the instrumentNo
	 */
	public String getInstrumentNo() {
		return instrumentNo;
	}
	/**
	 * @param instrumentNo the instrumentNo to set
	 */
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	/**
	 * @return the debit
	 */
	public BigDecimal getDebit() {
		return debit;
	}
	/**
	 * @param debit the debit to set
	 */
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	/**
	 * @return the credit
	 */
	public BigDecimal getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
        
}
