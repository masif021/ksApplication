package com.ks.application.service.soa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ks.application.model.soa.AccountInfo;
import com.ks.application.model.soa.SOAReadyPrintFile;
import com.ks.application.model.soa.SOARequest;
import com.ks.application.model.soa.TransactionRow;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class TxtParserAndPdfGenerateService {

	
	public void soaProcess(SOARequest soaRequest) throws Exception 
	{
        File inFolder = new File(soaRequest.getInputFolder());
        File outFolder = new File(soaRequest.getOutputFolder());

        if (!inFolder.exists() || !inFolder.isDirectory()) {
        	soaRequest.setStatusDesc("Invalid input directory not exists. dir : " + soaRequest.getInputFolder());
        	return ;
        }
        
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }
        else {
        	outFolder.delete();
        	outFolder.mkdirs();
        }
        File[] files = inFolder.listFiles(
                f -> f.isFile() && f.getName().toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) 
        {
        	soaRequest.setStatusDesc("No data found under input directory.  dir : " + soaRequest.getInputFolder());
            return ;
        }

        for (File txtFile : files)
        {
        	String txtFileName = txtFile.getName(); 
	    	int dotIndex = txtFileName.lastIndexOf('.');
	    	String fileNameWithoutExt = (dotIndex == -1) ? txtFileName : txtFileName.substring(0, dotIndex);
	    	
	    	
	        File folderWithFileName = new File(soaRequest.getOutputFolder() + File.separator + fileNameWithoutExt);
	        if (!folderWithFileName.exists())
	        {
	        	folderWithFileName.mkdirs();
	        }
//	        ProcessorUtil.createDirectory(folderWithFileName.getAbsolutePath());
	        parseAndGeneratePdf( txtFile.getAbsolutePath(),folderWithFileName.getAbsolutePath(),soaRequest);
//	        PdfMergerProcess.pdfMerger(folderWithFileName.getAbsolutePath(), folderWithFileName.getAbsolutePath() + File.separator + "Merged_File_" + new Date().getTime() + ".pdf" );
//	        ProcessorUtil.deleteDirectory(folderWithFileName.getAbsolutePath());
        } 
        
        soaRequest.setStatusDesc("Process done successfully, merged PDF geenerate on dir : " + outFolder.getAbsolutePath() + " : End Barcode Numeber : " + soaRequest.getBarCodeStartingNumber());
	}
    private void parseAndGeneratePdf(String txtFileName,String outputDir,SOARequest soaRequest) throws Exception {
        String barCodePrefix = soaRequest.getBarCodePrefix();
    	BigDecimal barCodeStartingNumber = soaRequest.getBarCodeStartingNumber();
    	
    	List<SOAReadyPrintFile> printableFileList = new ArrayList<>();
    	int randomNum = 0 ;
        try (BufferedReader br = new BufferedReader(new FileReader(txtFileName))){

            String line;
            boolean inAccountBlock = false;
            
            BigDecimal creditTrxCount =BigDecimal.ZERO;
            BigDecimal creditTrxAmount=BigDecimal.ZERO;
            BigDecimal debitTrxCount=BigDecimal.ZERO;
            BigDecimal debitTrxAmount=BigDecimal.ZERO;
            
            AccountInfo accountInfo = new AccountInfo();
            List<TransactionRow> transactions = new ArrayList<>();
            String barCodeLength = "0".repeat(soaRequest.getBarCodePrefix().length() > soaRequest.getBarCodeLength().intValue() ? soaRequest.getBarCodePrefix().length()  :  soaRequest.getBarCodeLength().intValue() - soaRequest.getBarCodePrefix().length() ) ;
            DecimalFormat dfBC = new DecimalFormat(barCodeLength);
            while ((line = br.readLine()) != null) 
            {
                line = line.trim();
                if (line.isEmpty()) continue;

                // -------- START ACCOUNT --------
                if (line.startsWith("+CUST-ACCOUNTINFO>")) {
                    inAccountBlock = true;
                 // -------- HEADER DATA (| delimited) --------
                    accountInfo = ProcessorUtil.parseAccountHeader(line);
                    accountInfo.setBarCode("*" + barCodePrefix  + dfBC.format(barCodeStartingNumber.doubleValue()) + "*");
                    accountInfo.setCategory( soaRequest.getCategory());
                    barCodeStartingNumber = barCodeStartingNumber.add(BigDecimal.ONE);
                    continue;
                }

                // -------- END ACCOUNT --------
                if (line.equals("+/CUST-ACCOUNTINFO>"))
                {
                	accountInfo.setCreditTrxAmount(creditTrxAmount);
                	accountInfo.setCreditTrxCount(creditTrxCount);
                	
                	accountInfo.setDebitTrxAmount(debitTrxAmount);
                	accountInfo.setDebitTrxCount(debitTrxCount);
                    inAccountBlock = false;
                    
                    // Generate PDF for this account
                    randomNum++;
                    SOAReadyPrintFile soaReadyPrintFile = new SOAReadyPrintFile();
                    soaReadyPrintFile.setAccountInfo(accountInfo);
                    soaReadyPrintFile.setTransactionRow(transactions);
                    String filePages  = ProcessorUtil.returnFilePages(transactions);
                    String pdfPath = outputDir + File.separator + filePages + "_" + randomNum+".pdf";
                    soaReadyPrintFile.setFilePath(pdfPath);
                    soaReadyPrintFile.setFilePages(filePages);
                    
                    System.out.println("File name : " + pdfPath + " - filePages : " + filePages);
                    
                    printableFileList.add(soaReadyPrintFile);

                    accountInfo = new AccountInfo();
                    transactions = new ArrayList<>();
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
                	TransactionRow row = ProcessorUtil.parseTxn(line);
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
	                    transactions.add(row);
                    }
	                    continue;
                }
            }
        }
        soaRequest.setBarCodeStartingNumber(barCodeStartingNumber);
        for (SOAReadyPrintFile soaReadyPrintFile : printableFileList)
        {
        	System.out.println(soaReadyPrintFile.getFilePages() + " - File Path : " + soaReadyPrintFile.getFilePath());
        	generatePdf(soaReadyPrintFile.getAccountInfo(), soaReadyPrintFile.getTransactionRow(), soaReadyPrintFile.getFilePath());
		}
        
    }
	
    private void generatePdf(AccountInfo account, List<TransactionRow> txns, String pdfPath) throws Exception {
    try {

    	Resource resource = new ClassPathResource("reports/soa.jrxml");
    	InputStream jrxml = resource.getInputStream();


		JasperReport report = JasperCompileManager.compileReport(jrxml);

		Map<String, Object> params = new HashMap<>();
		
		params.put("ClientName", account.getClientName());
		params.put("AccountNumber", account.getAccountNo());
		params.put("AccountTitle", account.getAccountTitle());
		params.put("ClientAddress", account.getAddress());
		params.put("IBAN", account.getIban());
		params.put("DateFrom", account.getFromDate());
		params.put("DateTo", account.getToDate());
		params.put("BarCode", account.getBarCode());
		params.put("Product", account.getProduct());
		params.put("Category", account.getCategory());
		params.put("BranchName", account.getBranch());
		params.put("RegionName", account.getRegion());
		params.put("Currency", account.getCurrency());
		
		params.put("debitTrxCount", account.getDebitTrxCount());
		params.put("debitTrxAmount", account.getDebitTrxAmount());
		params.put("creditTrxCount", account.getCreditTrxCount());
		params.put("creditTrxAmount", account.getCreditTrxAmount());
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		params.put( "TotalCreditTrxAmountFormatted","" + "Total " + account.getCreditTrxCount() + " Credit transactions of amount " +  df.format(account.getCreditTrxAmount() == null ? BigDecimal.ZERO : account.getCreditTrxAmount()) );
		params.put( "TotalDebitTrxAmountFormatted","" + "Total " + account.getDebitTrxCount() + " Debit transactions of amount " +  df.format(account.getDebitTrxAmount() == null ? BigDecimal.ZERO : account.getDebitTrxAmount()) );
		
		
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(txns);

		JasperPrint print = JasperFillManager.fillReport(report, params, ds);

		JasperExportManager.exportReportToPdfFile(print, pdfPath);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
}