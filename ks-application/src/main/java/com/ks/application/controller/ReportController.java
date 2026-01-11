package com.ks.application.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/report")
public class ReportController {


    @PostMapping("/print")
    public void print(@RequestParam("file") MultipartFile file,
                      HttpServletResponse response) throws Exception {

//        byte[] pdf = service.generate(file);
//        response.setContentType("application/pdf");
//        response.getOutputStream().write(pdf);
    }
}
