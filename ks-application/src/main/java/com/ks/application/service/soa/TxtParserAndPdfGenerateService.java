package com.ks.application.service.soa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ks.application.model.soa.AccountInfo;
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

	
    public void parseAndGeneratePdf(String txtPath,String outputDir,SOARequest soaRequest) throws Exception {

    	String barCodePrefix = soaRequest.getBarCodePrefix();
    	BigDecimal barCodeStartingNumber = soaRequest.getBarCodeStartingNumber();
    	int report_counter=0;
    	
    	int perPageRowCount = 30;
    	int centralBankImageRowCount = 4;
    	
        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))){

            String line;
            boolean inAccountBlock = false;
            
            BigDecimal creditTrxCount =BigDecimal.ZERO;
            BigDecimal creditTrxAmount=BigDecimal.ZERO;
            BigDecimal debitTrxCount=BigDecimal.ZERO;
            BigDecimal debitTrxAmount=BigDecimal.ZERO;
            
            AccountInfo accountInfo = new AccountInfo();
            List<TransactionRow> transactions = new ArrayList<>();
            DecimalFormat dfBC = new DecimalFormat("000000");
            while ((line = br.readLine()) != null) 
            {
                line = line.trim();
                if (line.isEmpty()) continue;

                // -------- START ACCOUNT --------
                if (line.startsWith("+CUST-ACCOUNTINFO>")) {
                    inAccountBlock = true;
                    report_counter++;
                 // -------- HEADER DATA (| delimited) --------
                    accountInfo = ProcessorUtil.parseAccountHeader(line);
                    accountInfo.setBarCode(barCodePrefix  + dfBC.format(barCodeStartingNumber.doubleValue()));
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
                    String pdfPath = outputDir + File.separator + accountInfo.getBarCode() + "_soa.pdf";
                    System.out.println("File name : " + pdfPath);
                    generatePdf(accountInfo, transactions, pdfPath);
                    
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
    }
	
    public void generatePdf(AccountInfo account, List<TransactionRow> txns, String pdfPath) throws Exception {
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