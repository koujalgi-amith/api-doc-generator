package com.amithkoujalgi.apidoc.core.test;

import com.amithkoujalgi.apidoc.core.entry.RZTRestAPIScanner;

public class TestAPIDocGen {
	public static void main(String[] args) throws Exception {
		RZTRestAPIScanner s = new RZTRestAPIScanner("Test Webapp");
		//s.scanPackages("com.amithkoujalgi");
		s.scanPackagesAndGenerate("com.amithkoujalgi", "/Users/amith/git/api-doc-generator/target");
	}
}
