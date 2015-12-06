package com.amithkoujalgi.apidoc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtils {
	public static void printJson(Object o) {
		System.out.println(new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(o));
	}

	public static String stringify(Object o) {
		return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(o);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return new Gson().fromJson(json, clazz);
	}
}
