package com.axreng.backend.bean;

public class PageCrawlerResult {

	private String url;
	private Boolean keyWordFound;
	
	public PageCrawlerResult(String url, Boolean keyWordFound) {
		this.url = url;
		this.keyWordFound = keyWordFound;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getKeyWordFound() {
		if(keyWordFound == null) {
			keyWordFound = Boolean.FALSE;
		}
		return keyWordFound;
	}
	public void setKeyWordFound(Boolean keyWordFound) {
		this.keyWordFound = keyWordFound;
	}
	
	
}
