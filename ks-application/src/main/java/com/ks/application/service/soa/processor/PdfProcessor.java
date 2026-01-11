package com.ks.application.service.soa.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.ks.application.common.util.AppProperties;
import com.ks.application.service.soa.BaseAccount;
import com.ks.application.service.soa.BaseTransaction;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Slf4j
public class PdfProcessor {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PdfProcessor.class);

    public static void generatePdf(BaseAccount account, List<BaseTransaction> txns, String pdfPath,String reportName ) throws Exception {
        	
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
		
		generateReport(params,txns,reportName,pdfPath);

    }
    
    
    public static void generateReport(Map<String, Object> params,List dataList ,String reportName , String outputPath ) {
    try {
    	
	    	JasperReport report = null;
	    	if (null != reportName && reportName.endsWith(".jasper") )
	    	{
	    		InputStream jasperStream = new ClassPathResource("reports/" + reportName ).getInputStream();
	    		report = (JasperReport) JRLoader.loadObject(jasperStream);
	    	}
	    	else 
	    	{
	    		Resource resource = new ClassPathResource("reports/" + reportName);
	        	InputStream jrxml = resource.getInputStream();
	    		report = JasperCompileManager.compileReport(jrxml);	
	    	}
	    		
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(dataList);
	
			JasperPrint print = JasperFillManager.fillReport(report, params, ds);
	
			JasperExportManager.exportReportToPdfFile(print, outputPath);
		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static void pdfMerger(String inputDir) throws IOException {

    	for (int j = 1; j<=10; j++) 
		 {
    		int k = j; 
	        File[] pdfFiles = new File(inputDir)
	                .listFiles(f ->
	                         (	f.getName().startsWith("Page_"+k)
	                        	|| f.getName().startsWith("Page_Multiple")
	                         )
	                        && f.getName().toLowerCase().endsWith(".pdf")
	                        && !f.getName().startsWith("Merged_File_Page_"+ k) 
	                        		 );
	        
	        if (pdfFiles == null || pdfFiles.length == 0)
	        {
	        	log.info("No .pdf file for Page_"+k);
	        	continue;
	        }
	     
	    String outputPdfFile = inputDir + File.separator + "Merged_File_Page_" +k+ ".pdf"; 
        // Ensure predictable batch order (important for banks)
        Arrays.sort(pdfFiles, Comparator.comparing(File::getName));

        PDFMergerUtility merger = new PDFMergerUtility();
        PDDocument destination = new PDDocument();

        List<PDDocument> openSources = new ArrayList<>();

        try {
            for (File pdf : pdfFiles) {

                PDDocument src = PDDocument.load(pdf);
                openSources.add(src);

                int pages = src.getNumberOfPages();

                // Append document SAFELY (deep clone)
                merger.appendDocument(destination, src);

                // If odd → add blank page
                if (pages % 2 != 0) {
                    PDRectangle mb = src.getPage(pages - 1).getMediaBox();
                    PDRectangle clone = new PDRectangle(
                            mb.getLowerLeftX(), mb.getLowerLeftY(),
                            mb.getWidth(), mb.getHeight()
                    );
                    destination.addPage(new PDPage(clone));
                }

                System.out.println("Merged Processed: " + pdf.getName() + " pages=" + pages);
            }

            destination.save(outputPdfFile);
            
	        } finally {
	            // Close everything LAST
	            for (PDDocument d : openSources) {
	                d.close();
	            }
	            destination.close();
	            
	            for (File pdf : pdfFiles) {
	            	deletePdf(pdf.getAbsolutePath());
	            }
	        }
    	}
        System.out.println("Merged PDF created successfully.");
    
    }
    
    public static void deletePdf(String pdf) {
        try {
			java.nio.file.Files.delete(java.nio.file.Paths.get(pdf));
		} catch (IOException e) {
			System.out.println("unable to delete file : " + pdf);
			e.printStackTrace();
		}
    }

	public static void converterPDFtoPSFile(String inputDir) throws Exception {

    	for (int j = 1; j<=10; j++){
    		int k = j; 
	        File[] pdfFiles = new File(inputDir)
	                .listFiles(f ->
	                         f.getName().toLowerCase().endsWith(".pdf")
	                        && f.getName().startsWith("Merged_File_Page_"+ k) 
	                        		 );
	        if (pdfFiles == null || pdfFiles.length == 0)
	        {
	        	System.out.println("No .pdf file for Page_"+k);
	        	continue;
	        }
	        
	        for (File pdf : pdfFiles) {
	        	System.out.println("process file "+ pdf.getAbsolutePath());
	        	
	        	String fullPath = pdf.getAbsolutePath();     
	        	String fileName = pdf.getName();             
	        	String parentPath = pdf.getParent();         
	        	String extension ="pdf";
	        	int dot = fileName.lastIndexOf('.');
	        	if (dot > 0) {
	        		fileName = fileName.substring(0, dot);        // statement_20260111
	        	    extension = pdf.getName().substring(dot + 1);  // pdf
	        	}	        	
	        	convertPdfToPs(pdf.getAbsolutePath(), parentPath + File.separator + fileName + ".ps");
	        }
		 }
	}

 
    public static void convertPdfToPs(String pdfPath, String psPath) throws Exception {
    	
    	
    	  String[] command = {
    			  AppProperties.get().getGsCommand(),
    		        "-dNOPAUSE",
    		        "-dBATCH",
    		        "-sDEVICE=ps2write",
    		        "-dLevel3",
    		        "-dEmbedAllFonts=true",
    		        "-sOutputFile=" + psPath,
    		        pdfPath
    		    };

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line); // for logging
//        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("PDF to PS conversion failed with code " + exitCode);
        }

        PdfProcessor.addPrintControls(psPath, true, 2);
        System.out.println("PostScript file created: " + psPath);
    }
    
    
    public static void addPrintControls(String psFile,boolean duplex,int trayNumber) throws Exception {

        String ps = new String(Files.readAllBytes(Paths.get(psFile)));

        String insert =
            "%%BeginSetup\n" +
            "<< /Duplex " + (duplex?"true":"false")+ " /Tumble false /MediaPosition " + trayNumber + " >> setpagedevice\n" +
            "%%EndSetup\n";

        ps = ps.replace("%!PS-Adobe-3.0", "%!PS-Adobe-3.0\n" + insert);

        Files.write(Paths.get(psFile), ps.getBytes());
    }
    
    public static void printPS(String printer, String psFile) throws Exception {
        String cmd = "print /D:\"" + printer + "\" \"" + psFile + "\"";
        Runtime.getRuntime().exec(cmd);
    }    
    
}
