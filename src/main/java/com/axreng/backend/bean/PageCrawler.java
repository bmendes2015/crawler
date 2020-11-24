package com.axreng.backend.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageCrawler implements Callable<Void> {

	private static final Pattern anchors = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*)\"");

	private Crawler crawler;
	private String url;

	public PageCrawler(Crawler crawler, String url) {
		this.crawler = crawler;
		this.url = url;
	}

	@Override
	public Void call() throws Exception {
		
		if(this.crawler.checkStopCrawler()) {
			return null;
		}

		URL urlObj = new URL(this.url);
		HttpURLConnection yc = (HttpURLConnection) urlObj.openConnection();

		InputStream in = null;
		if (yc.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
			in = yc.getErrorStream();
		} else {
			in = yc.getInputStream();
		}

		BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
		StringBuilder document = new StringBuilder();
		String inputLine;
		while ((inputLine = buffer.readLine()) != null) {
			document.append(inputLine);
		}
		in.close();

		matcherKeyWord(document.toString());
		matcherAnchors(document.toString());
		
		return null;

	}

	private void matcherKeyWord(String document) {
		Matcher mKey = Pattern.compile(Pattern.quote(this.crawler.getKeyWord()), Pattern.CASE_INSENSITIVE)
				.matcher(document);

		if (mKey.find()) {			
			this.crawler.updatePageCrawlerResultPositive(this.url);
		}
	}

	private void matcherAnchors(String document) throws URISyntaxException {

		Matcher m = anchors.matcher(document);

		URI newUrlBase = null;
		if (!this.crawler.getBaseURL().endsWith("/")) {
			newUrlBase = new URI(this.crawler.getBaseURL() + "/");
		} else {
			newUrlBase = new URI(this.crawler.getBaseURL());
		}

		while (m.find()) {
			for (int i = 1; i <= m.groupCount(); i++) {

				String group = m.group(i).trim();

				if (group.matches("^[./].+")) {
					group = group.replaceFirst("[./]+", "");
				}

				try {
					String newPage = newUrlBase.resolve(group).toString().toLowerCase();

					if (newPage.startsWith(this.crawler.getBaseURL())) {
						if (!this.crawler.getPageCrawlers().containsKey(newPage)) {
							this.crawler.getPageCrawlers().put(newPage,
									new PageCrawlerResult(newPage, Boolean.FALSE));
							
							this.crawler.getPool().submit(new PageCrawler(crawler, newPage));
							
						}
					}
				} catch (IllegalArgumentException e) {
				}
			}
		}
	}

}
