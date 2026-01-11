package com.ks.application.service.soa.engine;

import com.ks.application.model.soa.SOARequest;

public abstract class SOAProcessor implements Processor {

	private String fileName;
	private String outputDir;
	private SOARequest soaRequest;
	
	public void execute() throws Exception {
		
		parser();
		processor();
		merger();
		converter();
		
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @param outputDir the outputDir to set
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	/**
	 * @return the soaRequest
	 */
	public SOARequest getSoaRequest() {
		return soaRequest;
	}

	/**
	 * @param soaRequest the soaRequest to set
	 */
	public void setSoaRequest(SOARequest soaRequest) {
		this.soaRequest = soaRequest;
	}
}
