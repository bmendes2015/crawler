package com.axreng.backend.bean;

import java.util.HashSet;
import java.util.Set;

public class CrawlerStatusParameterOUT {

	private String id;
	private Status status;
	private Set<String> urls;
	
	public CrawlerStatusParameterOUT(Crawler crawler) {
		this.id = crawler.getName();
		this.status = crawler.getStatus();
		this.urls = crawler.getUrlsWithKeyWord();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Set<String> getUrls() {
		if(urls == null) {
			urls = new HashSet<>();
		}
		return urls;
	}
	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}
}
