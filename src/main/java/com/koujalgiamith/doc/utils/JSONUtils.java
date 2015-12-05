package com.koujalgiamith.doc.utils;

import com.google.gson.GsonBuilder;

public class JSONUtils {
	public static void printJson(Object o) {
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(o));
	}
}
