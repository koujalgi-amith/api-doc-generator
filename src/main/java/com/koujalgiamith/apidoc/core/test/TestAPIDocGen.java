package com.koujalgiamith.apidoc.core.test;

import com.koujalgiamith.apidoc.core.entry.RZTRestAPIScanner;

public class TestAPIDocGen {
	public static void main(String[] args) throws Exception {
		RZTRestAPIScanner s = new RZTRestAPIScanner("Test Webapp");
		//s.scanPackages("com.koujalgiamith");
		s.scanPackagesAndGenerate("com.koujalgiamith", "/Users/amith/git/api-doc-generator/target");
	}
}
