package com.ks.application.model.soa;

import java.util.List;

public class SOADetails {
	
	private AccountInfo accountInfo;
	private List<TransactionRow> transactionsList;
	/**
	 * @return the accountInfo
	 */
	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
	/**
	 * @param accountInfo the accountInfo to set
	 */
	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	/**
	 * @return the transactionsList
	 */
	public List<TransactionRow> getTransactionsList() {
		return transactionsList;
	}
	/**
	 * @param transactionsList the transactionsList to set
	 */
	public void setTransactionsList(List<TransactionRow> transactionsList) {
		this.transactionsList = transactionsList;
	}
	
	
}
