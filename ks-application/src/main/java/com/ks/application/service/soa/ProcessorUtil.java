package com.ks.application.service.soa;

import java.math.BigDecimal;

import com.ks.application.common.util.StringUtil;
import com.ks.application.model.soa.AccountInfo;
import com.ks.application.model.soa.TransactionRow;

public class ProcessorUtil
{
	public static AccountInfo parseAccountHeader(String line) {
	
		  // Remove tag
	    line = line.replace("+CUST-ACCOUNTINFO>|", "");

	    String[] parts = line.split("\\|");
	
	    if (parts.length < 9) 
	    {
	    	System.out.println("Invalid account data at line :" + line );
	    	return null;
	    }
	    
	    AccountInfo info = new AccountInfo();
	    info.setBranch(StringUtil.isNotEmpty(parts[0]) ? parts[0].trim() : parts[0] );
	    info.setRegion(StringUtil.isNotEmpty(parts[1]) ? parts[1].trim() : parts[1] );
	    info.setClientName(StringUtil.isNotEmpty(parts[2]) ? parts[2].trim() : parts[2] );
	    info.setAccountTitle(StringUtil.isNotEmpty(parts[2]) ? parts[2].trim() : parts[2] );
	    info.setProduct(StringUtil.isNotEmpty(parts[3]) ? parts[3].trim() : parts[3] );
	    info.setAddress(StringUtil.isNotEmpty(parts[4]) ? parts[4].trim() : parts[4] );
	    info.setAccountNo(StringUtil.maskString(parts[5].trim(),4,9,'*'));
	    info.setIban(StringUtil.maskString(parts[6].trim(),10,21,'*'));
	    info.setFromDate(parts[7].trim());
	    info.setToDate(parts[8].trim());

	    return info;
	}
	 public static  TransactionRow parseTxn(String line) {

		try {
	        // +TXN>|date|desc|inst|debit|credit|balance|+/TXN>
	       String lineToBeProcess = line.replace("+TXN>|", "")
	                   					.replace("|+/TXN>", "");
	
		    // Format:
		    // DATE|DESC|INSTNO|DEBIT|CREDIT|BALANCE
		    String[] parts = lineToBeProcess.split("\\|", -1); // IMPORTANT (-1 keeps empty fields)
	
		    if ( parts.length < 6)
		    {
		    	System.out.println("Invalid transaction data  at line :" + line );
		    	return null;
		    }
		    
		    TransactionRow row = new TransactionRow();
		    row.setDate(parts[0]);
		    row.setParticulars(parts[1]);
		    row.setInstrumentNo(StringUtil.isNotEmpty(parts[2]) ? parts[2] : null);
		    row.setDebit(StringUtil.isNotEmpty(parts[3]) ? new BigDecimal(parts[3]) : null);
		    row.setCredit(StringUtil.isNotEmpty(parts[4]) ? new BigDecimal(parts[4]) : null);
		    row.setBalance(StringUtil.isNotEmpty(parts[5]) ? new BigDecimal(parts[5]) : null);
		    return row;
		}catch(Exception ex) {
			System.out.println("Error in line ::: "+ line);
			ex.printStackTrace();
		}
		return null;
	    
	}
}