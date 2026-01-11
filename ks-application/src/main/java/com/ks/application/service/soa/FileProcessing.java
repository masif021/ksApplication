package com.ks.application.service.soa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ks.application.common.util.FileWriteUtil;
import com.ks.application.model.soa.SOARequest;
import com.ks.application.service.soa.engine.SOAProcessor;
import com.ks.application.service.soa.text.AccountInfo;
import com.ks.application.service.soa.text.SOAReadyPrintFile;
import com.ks.application.service.soa.text.TextSOAProcessor;
import com.ks.application.service.soa.text.TransactionRow;
import com.ks.application.service.soa.xml.XMLSOAProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessing {
	
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

        File[] files = inFolder.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".txt"));

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
	        process( txtFile.getAbsolutePath(),folderWithFileName.getAbsolutePath(),soaRequest);
        } 
        soaRequest.setBarCodeEndingNumber(soaRequest.getBarCodeStartingNumber());
        soaRequest.setStatusDesc("Process done successfully, files geenerated on dir : " + outFolder.getAbsolutePath() + " : End Barcode Numeber : " + soaRequest.getBarCodeEndingNumber());
	}
	
    private void process(String fileName,String outputDir,SOARequest soaRequest) throws Exception 
    {
    	SOAProcessor processor = null;
    	switch(soaRequest.getPrintSOAFor())
    	{
    	case "nbp":
    		 processor = new TextSOAProcessor();
    		break;
    	case "hmb":
    		processor = new XMLSOAProcessor();
    		break;
    	} 
    	if ( null == processor )
    	{
    		soaRequest.setStatusDesc("Invalid Statement of Account choice.");
    		return ; 
    	}
    	
    	processor.setOutputDir(outputDir);
    	processor.setFileName(fileName);
    	processor.setSoaRequest(soaRequest);
    	processor.execute();
    	
    }

   
}