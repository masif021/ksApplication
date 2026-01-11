package com.ks.application.service.soa.text;

import java.util.List;

import com.ks.application.service.soa.BaseAccount;
import com.ks.application.service.soa.BaseTransaction;

public class SOAReadyPrintFile {
	
	private BaseAccount baseAccount;
	private List<BaseTransaction> baseTransaction;
	private String filePath;
	private String filePages;
	/**
	 * @return the baseAccount
	 */
	public BaseAccount getBaseAccount() {
		return baseAccount;
	}
	/**
	 * @param baseAccount the baseAccount to set
	 */
	public void setBaseAccount(BaseAccount baseAccount) {
		this.baseAccount = baseAccount;
	}
	/**
	 * @return the baseTransaction
	 */
	public List<BaseTransaction> getBaseTransaction() {
		return baseTransaction;
	}
	/**
	 * @param baseTransaction the baseTransaction to set
	 */
	public void setBaseTransaction(List<BaseTransaction> baseTransaction) {
		this.baseTransaction = baseTransaction;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the filePages
	 */
	public String getFilePages() {
		return filePages;
	}
	/**
	 * @param filePages the filePages to set
	 */
	public void setFilePages(String filePages) {
		this.filePages = filePages;
	}
	

	
}
