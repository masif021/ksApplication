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
        parserService.soaProcess(soaRequest); 
        return soaRequest;
    }
}
