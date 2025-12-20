package com.ks.application.service.soa;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.application.model.soa.SOARequest;

@Service
public class SOAService {
	
	@Autowired
	public TxtParserAndPdfGenerateService parserService;

    public SOARequest process(SOARequest soaRequest) throws Exception {
    	
        List<Map<String, String>> rows = new ArrayList<>();

        File inFolder = new File(soaRequest.getInputFolder());
        File outFolder = new File(soaRequest.getOutputFolder());

        if (!inFolder.exists() || !inFolder.isDirectory()) {
        	soaRequest.setStatusDesc("Invalid input directory not exists. dir : " + soaRequest.getInputFolder());
        	return soaRequest;
        }

        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }
        
        File mergedFolder = new File(soaRequest.getOutputFolder() + File.separator + "merged");
        if (!mergedFolder.exists()) {
        	mergedFolder.mkdirs();
        }
        
        File[] files = inFolder.listFiles(
                f -> f.isFile() && f.getName().toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
        	soaRequest.setStatusDesc("No data found under input directory.  dir : " + soaRequest.getInputFolder());
            return soaRequest;
        }

        for (File txtFile : files) {
            parserService.parseAndGeneratePdf( txtFile.getAbsolutePath(),outFolder.getAbsolutePath(),soaRequest);
        }
        
        PdfMergerProcess.pdfMerger(outFolder.getAbsolutePath(), mergedFolder.getAbsolutePath() + File.separator + "Merged_File_" + new Date().getTime() + ".pdf" );
        soaRequest.setStatusDesc("Process done successfully, merged PDF geenerate on dir : " + mergedFolder.getAbsolutePath());
        return soaRequest;
    }
}
