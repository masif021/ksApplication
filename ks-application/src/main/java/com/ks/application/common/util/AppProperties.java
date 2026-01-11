package com.ks.application.common.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ksApplication.properties")
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	@Value("${nbp.soa.report}")
    private String nbpSOAReport;

	@Value("${hmb.soa.report}")
    private String hmbSOAReport;
	
	@Value("${printer.gsCommand}")
	private String gsCommand;

	
    private static AppProperties INSTANCE;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    public static AppProperties get() {
        return INSTANCE;
    }

	/**
	 * @return the nbpSOAReport
	 */
	public String getNbpSOAReport() {
		return nbpSOAReport;
	}

	/**
	 * @param nbpSOAReport the nbpSOAReport to set
	 */
	public void setNbpSOAReport(String nbpSOAReport) {
		this.nbpSOAReport = nbpSOAReport;
	}

	/**
	 * @return the hmbSOAReport
	 */
	public String getHmbSOAReport() {
		return hmbSOAReport;
	}

	/**
	 * @param hmbSOAReport the hmbSOAReport to set
	 */
	public void setHmbSOAReport(String hmbSOAReport) {
		this.hmbSOAReport = hmbSOAReport;
	}

	/**
	 * @return the gsCommand
	 */
	public String getGsCommand() {
		return gsCommand;
	}

	/**
	 * @param gsCommand the gsCommand to set
	 */
	public void setGsCommand(String gsCommand) {
		this.gsCommand = gsCommand;
	}

    
}
