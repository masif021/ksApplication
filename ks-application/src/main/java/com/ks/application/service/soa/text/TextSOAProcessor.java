package com.ks.application.service.soa.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ks.application.common.util.AppProperties;
import com.ks.application.common.util.FileWriteUtil;
import com.ks.application.service.soa.BaseTransaction;
import com.ks.application.service.soa.SoaUtil;
import com.ks.application.service.soa.engine.SOAProcessor;
import com.ks.application.service.soa.processor.PdfProcessor;

public class TextSOAProcessor extends SOAProcessor{

	List<SOAReadyPrintFile> printableFileList = new ArrayList<>();
	
	@Override
	public void parser() throws Exception 
	{
		String barCodePrefix = getSoaRequest().getBarCodePrefix();
    	BigDecimal barCodeStartingNumber = getSoaRequest().getBarCodeStartingNumber();
    	String errorfileName = getOutputDir() + File.separator + "Error.txt";
    	
    	int randomNum = 0 ;
        try (BufferedReader br = new BufferedReader(new FileReader(getFileName()))){

            String line;
            boolean inAccountBlock = false;
            
            BigDecimal creditTrxCount =BigDecimal.ZERO;
            BigDecimal creditTrxAmount=BigDecimal.ZERO;
            BigDecimal debitTrxCount=BigDecimal.ZERO;
            BigDecimal debitTrxAmount=BigDecimal.ZERO;
            
            AccountInfo accountInfo = new AccountInfo();
            List<BaseTransaction> transactionList = new ArrayList<>();
            String barCodeLength = "0".repeat(getSoaRequest().getBarCodePrefix().length() > getSoaRequest().getBarCodeLength().intValue() ? getSoaRequest().getBarCodePrefix().length()  :  getSoaRequest().getBarCodeLength().intValue() - getSoaRequest().getBarCodePrefix().length() ) ;
            DecimalFormat dfBC = new DecimalFormat(barCodeLength);

            while ((line = br.readLine()) != null) 
            {
                line = line.trim();
                if (line.isEmpty()) 
                	continue;

                // -------- START ACCOUNT --------
                if (line.startsWith("+CUST-ACCOUNTINFO>"))
                {
                    inAccountBlock = true;
                 // -------- HEADER DATA (| delimited) --------
                    accountInfo = DataProcessor.parseAccountHeader(line);
                    if (null == accountInfo)
                    {
                    	FileWriteUtil.write(errorfileName, line);
                    	continue;
                    }
                    accountInfo.setBarCode("*" + barCodePrefix  + dfBC.format(barCodeStartingNumber.doubleValue()) + "*");
                    accountInfo.setCategory( getSoaRequest().getCategory());
                    barCodeStartingNumber = barCodeStartingNumber.add(BigDecimal.ONE);
                    continue;
                }

                // -------- END ACCOUNT --------
                if (line.equals("+/CUST-ACCOUNTINFO>"))
                {
                	inAccountBlock = false;
               //// when there is an error in account info then this account should not added under printableFileList 
                	if (null == accountInfo)
                	{
                		accountInfo = new AccountInfo();
                		transactionList = new ArrayList<>();
                        creditTrxCount= BigDecimal.ZERO;
                        creditTrxAmount=BigDecimal.ZERO;
                        debitTrxCount=BigDecimal.ZERO;
                        debitTrxAmount=BigDecimal.ZERO;
                		continue;
                	}
                	
                	accountInfo.setCreditTrxAmount(creditTrxAmount);
                	accountInfo.setCreditTrxCount(creditTrxCount);
                	
                	accountInfo.setDebitTrxAmount(debitTrxAmount);
                	accountInfo.setDebitTrxCount(debitTrxCount);
                    // Generate PDF for this account
                    randomNum++;
                    SOAReadyPrintFile soaReadyPrintFile = new SOAReadyPrintFile();
                    soaReadyPrintFile.setBaseAccount(accountInfo);
                    soaReadyPrintFile.setBaseTransaction(transactionList);
                    String filePages  = SoaUtil.returnFilePages(transactionList,getSoaRequest());
                    String pdfPath = getOutputDir() + File.separator + filePages + "_" + randomNum+".pdf";
                    
                    soaReadyPrintFile.setFilePath(pdfPath);
                    soaReadyPrintFile.setFilePages(filePages);
                    
                    System.out.println("File name : " + pdfPath + " - filePages : " + filePages);
                    
                    getPrintableFileList().add(soaReadyPrintFile);
                
                    accountInfo = new AccountInfo();
                    transactionList = new ArrayList<>();
                    creditTrxCount= BigDecimal.ZERO;
                    creditTrxAmount=BigDecimal.ZERO;
                    debitTrxCount=BigDecimal.ZERO;
                    debitTrxAmount=BigDecimal.ZERO;
                    continue;
                }
                if (!inAccountBlock) {
                    continue;
                }

                // -------- TRANSACTION START --------
                if (line.startsWith("+TXN>"))
                {
                	TransactionRow row = DataProcessor.parseTxn(line);
                    if ( null != row)
                    {
                    	if ( null != row.getDebit() &&  row.getDebit().doubleValue() > 0 )
                    	{
                    		debitTrxCount = debitTrxCount.add(BigDecimal.ONE);
                    		debitTrxAmount = debitTrxAmount.add(row.getDebit());
                    	}
                    	if ( null != row.getCredit() && row.getCredit().doubleValue() > 0 )
                    	{
                    		creditTrxCount = creditTrxCount.add(BigDecimal.ONE);
                    		creditTrxAmount = creditTrxAmount.add(row.getCredit());
                    	}
//                    	System.out.println("Date :" + row.getDate() + " - Particulars: " + row.getParticulars() + " - Inst. # " + row.getInstrumentNo() + " - trx debit amount : " + row.getDebit() + " - credit amount : "+ row.getCredit() + " - debitTrxCount : " + debitTrxCount  + " - debitTrxAmount : "  + debitTrxAmount + " - creditTrxCount : " + creditTrxCount  + " - creditTrxAmount : "  + creditTrxAmount  + " - Balance : " + row.getBalance());
                    	transactionList.add(row);
                    }
	                continue;
                }
            }
        }
        getSoaRequest().setBarCodeStartingNumber(barCodeStartingNumber);
	}

	@Override
	public void processor() throws Exception {
	   for (SOAReadyPrintFile soaReadyPrintFile : getPrintableFileList())
	    {
	    	System.out.println(soaReadyPrintFile.getFilePages() + " - File Path : " + soaReadyPrintFile.getFilePath());
	    	String reportName = AppProperties.get().getNbpSOAReport();
	    	PdfProcessor.generatePdf(soaReadyPrintFile.getBaseAccount(), soaReadyPrintFile.getBaseTransaction(), soaReadyPrintFile.getFilePath(),reportName);
		}
		
	}

	@Override
	public void merger() throws IOException {
        PdfProcessor.pdfMerger(getOutputDir());
	}

	@Override
	public void converter() throws Exception {
		if ( !getSoaRequest().isConvertIntoPS() )
				return ; 
		
		PdfProcessor.converterPDFtoPSFile(getOutputDir());
	}

	/**
	 * @return the printableFileList
	 */
	public List<SOAReadyPrintFile> getPrintableFileList() {
		return printableFileList;
	}

	/**
	 * @param printableFileList the printableFileList to set
	 */
	public void setPrintableFileList(List<SOAReadyPrintFile> printableFileList) {
		this.printableFileList = printableFileList;
	}

}
