package com.axreng.backend.bean;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Crawler extends Thread {

	private ExecutorService pool = Executors.newFixedThreadPool(100);
	
	private EnvironmentParameter environmentParameter;
	private String keyWord;
	private AtomicLong countResults;
	private ConcurrentHashMap<String, PageCrawlerResult> pageCrawlers;
	private AtomicBoolean stopCrawler;
	
	public Crawler(EnvironmentParameter environmentParameter, String keyWord) throws NoSuchAlgorithmException {
		this.environmentParameter = environmentParameter;
		this.keyWord = keyWord;
		this.pageCrawlers = new ConcurrentHashMap<>();
		this.countResults = new AtomicLong();
		this.stopCrawler = new AtomicBoolean();
		setName(Identify.getInstance().generateID());
	}

	public Status getStatus() {
		return this.pool.isTerminated() ? Status.done : Status.active;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public ConcurrentHashMap<String, PageCrawlerResult> getPageCrawlers() {
		if (pageCrawlers == null) {
			pageCrawlers = new ConcurrentHashMap<>();
		}
		return pageCrawlers;
	}

	public void setPageCrawlers(ConcurrentHashMap<String, PageCrawlerResult> pageCrawlers) {
		this.pageCrawlers = pageCrawlers;
	}

	public EnvironmentParameter getEnvironmentParameter() {
		return environmentParameter;
	}

	public void setEnvironmentParameter(EnvironmentParameter environmentParameter) {
		this.environmentParameter = environmentParameter;
	}
	
	public String getBaseURL() {
		return getEnvironmentParameter().getBaseURL().toString().toLowerCase();
	}
	
	public void run() {
		
		pool.submit(new PageCrawler(this, getBaseURL()));	
		
		try {
			sleep(5000);
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public Set<String> getUrlsWithKeyWord() {
		
		return getPageCrawlers().entrySet().stream().filter(map -> map.getValue().getKeyWordFound())
				.map(Map.Entry::getKey).collect(Collectors.toSet());   
	}
	
	public ExecutorService getPool() {
		return this.pool;
	}

	private AtomicLong getCountResults() {
		return countResults;
	}
	
	private AtomicBoolean getStopCrawler() {
		return stopCrawler;
	}

	public synchronized void incrementCountResults() {
		if(!getEnvironmentParameter().isUnlimited() &&  this.countResults.longValue() <= getEnvironmentParameter().getMaxResults().longValue()) {
			this.countResults.incrementAndGet();			
		}
	}
	
	public synchronized void updatePageCrawlerResultPositive(String url) {
		
		if(!checkStopCrawler()) {
			getPageCrawlers().get(url).setKeyWordFound(Boolean.TRUE);
			incrementCountResults();
			
			if(!getEnvironmentParameter().isUnlimited() && getCountResults().longValue() == getEnvironmentParameter().getMaxResults().longValue()) {
				getStopCrawler().set(Boolean.TRUE);
			}
		}	
	}
	
	public boolean checkStopCrawler() {
		return getStopCrawler().get();
	}
	
}
