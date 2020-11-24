package com.axreng.backend.controller;

import static com.axreng.backend.util.JsonUtil.json;
import static com.axreng.backend.util.JsonUtil.toJson;
import static spark.Spark.afterAfter;
import static spark.Spark.exception;
import static spark.Spark.post;
import static spark.Spark.get;

import java.net.HttpURLConnection;

import com.axreng.backend.bean.Crawler;
import com.axreng.backend.bean.CrawlerStartParameterIN;
import com.axreng.backend.bean.CrawlerStartParameterOUT;
import com.axreng.backend.bean.CrawlerStatusParameterOUT;
import com.axreng.backend.bean.EnvironmentParameter;
import com.axreng.backend.service.CrawlerService;
import com.axreng.backend.util.JsonUtil;
import com.axreng.backend.util.ResponseError;

public class CrawlerController {

	public CrawlerController(final CrawlerService crawlerService, EnvironmentParameter environmentParameter) {

		post("/crawl", (req, res) -> {

			CrawlerStartParameterIN in = JsonUtil.fromJson(req.body(), CrawlerStartParameterIN.class);
			in.isValid();

			String id = crawlerService.start(environmentParameter, in.getKeyword());

			return new CrawlerStartParameterOUT(id);

		}, json());
		
		get("/crawl/:id", (req, res) -> {

			Crawler crawler = crawlerService.status(req.params("id"));

			return new CrawlerStatusParameterOUT(crawler);

		}, json());
		

		afterAfter((req, res) -> {
			res.type("application/json");
		});

		exception(IllegalArgumentException.class, (e, req, res) -> {
			res.status(HttpURLConnection.HTTP_BAD_REQUEST);
			res.body(toJson(new ResponseError(res, e)));
		});
	}

}
