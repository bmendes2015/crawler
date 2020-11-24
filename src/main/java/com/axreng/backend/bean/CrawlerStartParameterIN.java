package com.axreng.backend.bean;

public class CrawlerStartParameterIN {
	
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public boolean isValid() {
        if(!(this.keyword != null && !keyword.isEmpty() && keyword.length() >= 4 && keyword.length() <= 32)) {
        	throw new IllegalArgumentException("Field 'keyword' is required (from 4 up to 32 chars)");
        }
        
        return true;
    }
	
}
