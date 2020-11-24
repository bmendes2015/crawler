package com.axreng.backend;


import static spark.Spark.stop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axreng.backend.bean.EnvironmentParameter;
import com.axreng.backend.controller.CrawlerController;
import com.axreng.backend.service.CrawlerService;

public class Main {
	
	public static void main(String[] args) {
    	
		EnvironmentParameter env = validEnvironmentParameter();
		if(env != null) {
			new CrawlerController(new CrawlerService(), env);
		}	
    }
	
	static EnvironmentParameter validEnvironmentParameter() {
		
		Logger logger = LoggerFactory.getLogger(Main.class);
		
		try {
    		
    		return new EnvironmentParameter.Builder(System.getenv("BASE_URL"))
					.maxResults(System.getenv("MAX_RESULTS")).build();
    		
    	} catch (IllegalArgumentException e) {
    		logger.error(e.getMessage());
    		logger.error("this application is responsible for "
				+ "validation of the values ​​of environment variables during their initialization. "
				+ "If invalid values ​​are used for a mandatory variable, "
				+ "the execution should end.");
    		
    		stop();						
			
		}
		
		return null;	
	}
}
