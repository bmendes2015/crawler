package com.axreng.backend.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.axreng.backend.bean.Crawler;
import com.axreng.backend.bean.EnvironmentParameter;

public class CrawlerService {
	
	private HashMap<String, Crawler> crawlers = new HashMap<>();
	
	private Crawler createCrawler(EnvironmentParameter environmentParameter, String keyWord) throws NoSuchAlgorithmException {
		
		Crawler crawler = new Crawler(environmentParameter, keyWord);
		crawlers.put(crawler.getName(),crawler);
		return crawler;
	}
	
	private Crawler getCrawlerById(String id) throws NoSuchAlgorithmException {
		
		return crawlers.get(id);
		
	}
	
	public String start(EnvironmentParameter environmentParameter, String keyWord) throws NoSuchAlgorithmException {
		
		Crawler crawler = createCrawler(environmentParameter, keyWord);
		crawler.start();
		return crawler.getName();
		
	}
	
	
	public Crawler status(String id) throws NoSuchAlgorithmException {
		
		return getCrawlerById(id);
		
	}
	

	
	
}
