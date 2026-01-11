package com.ks.application.service.soa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.application.model.soa.SOARequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SOAService {
	
	@Autowired
	public FileProcessing parserService;

    public SOARequest process(SOARequest soaRequest) throws Exception {
        parserService.soaProcess(soaRequest); 
        return soaRequest;
    }
}
