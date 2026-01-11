package com.ks.application.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ks.application.model.soa.SOARequest;
import com.ks.application.service.soa.SOAService;

@RestController
@RequestMapping("/api/soa")
public class SOAController {

    @Autowired
    private SOAService service;

    @PostMapping("/process")
    public SOARequest print(@RequestBody SOARequest soaRequest,
                      HttpServletResponse response) throws Exception {

    	SOARequest soaResponse = service.process(soaRequest);
        return soaResponse ; 
    }
}
