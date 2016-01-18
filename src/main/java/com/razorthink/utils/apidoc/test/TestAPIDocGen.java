package com.razorthink.utils.apidoc.test;

import java.io.File;

import com.razorthink.utils.apidoc.core.RESTAPIScanner;

public class TestAPIDocGen {

	public static void main(String[] args) throws Exception {
		RESTAPIScanner s = new RESTAPIScanner("Test Webapp");
		s.scanPackagesAndGenerate(new String[] { "com.razorthink" }, System.getProperty("user.home") + File.separator
				+ "api-doc" + File.separator + TestAPIDocGen.class.getSimpleName());
	}
}
