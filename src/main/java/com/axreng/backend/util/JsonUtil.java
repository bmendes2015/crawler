package com.axreng.backend.util;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonUtil {

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}
	
	public static final <V> V fromJson(String json, Class<V> type) {
		return new Gson().fromJson(json, type);
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}
