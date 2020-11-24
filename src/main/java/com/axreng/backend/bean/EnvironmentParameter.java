package com.axreng.backend.bean;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class EnvironmentParameter {
	
	private static final Long DEFAULT_MAX_RESULTS = -1l;
	
	private URI baseURL;
	private Long maxResults;	
	
	public static class Builder {
		
		private final URI baseURL;

		private Long maxResults = DEFAULT_MAX_RESULTS;
		
		public Builder(String baseURL) throws IllegalArgumentException {
			
			try {	
				 
				URI uri = new URI(baseURL);
				URL url = uri.toURL();
				
				if(!("http".equalsIgnoreCase(url.getProtocol()) || "https".equalsIgnoreCase(url.getProtocol()))){
					throw new MalformedURLException("Only accept http or https protocol.");
				}
				
				this.baseURL = uri;
			}catch (NullPointerException | URISyntaxException | MalformedURLException e) {
				throw new IllegalArgumentException("Invalid BASE_URL " + baseURL + " parameter.");
			}	
		}

		public Builder maxResults(String maxResults) {
			
			Long result = null;
			
			try {
				result = Long.parseLong(maxResults);
				
				if(result < DEFAULT_MAX_RESULTS) {
					result = DEFAULT_MAX_RESULTS;
				}
				
			}catch (NumberFormatException e) {
				result = DEFAULT_MAX_RESULTS;				
			}
				
			this.maxResults = result;
			return this;
		}
		
		public EnvironmentParameter build() {
			return new EnvironmentParameter(this);
		}
		
	}
	
	private EnvironmentParameter(Builder builder) {
		this.baseURL = builder.baseURL;
		this.maxResults = builder.maxResults;
	}

	public URI getBaseURL() {
		return baseURL;
	}

	public Long getMaxResults() {
		return maxResults;
	}
	
	public boolean isUnlimited() {
		return getMaxResults().longValue() == DEFAULT_MAX_RESULTS.longValue();
	}
}
