package com.ks.application.service.soa.engine;

public interface Processor {

	public void parser() throws Exception, Exception;
	public void processor() throws Exception, Exception;
	public void merger() throws Exception, Exception;
	public void converter() throws Exception, Exception;
}