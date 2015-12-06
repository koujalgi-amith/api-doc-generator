package com.amithkoujalgi.apidoc.entry;

import java.io.IOException;

import com.amithkoujalgi.apidoc.core.RZTRestAPIScanner;

public class APISpecBuilderMain {
	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		if (args.length < 3) {
			System.err.println("Error!\n\tMissing args: [project name] [package to scan] [destination dir for docs]");
			System.exit(0);
		}
		RZTRestAPIScanner apiScanner = new RZTRestAPIScanner(args[0]);
		apiScanner.scanPackagesAndGenerate(args[1], args[2]);
	}
}
