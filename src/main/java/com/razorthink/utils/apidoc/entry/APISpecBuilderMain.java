package com.razorthink.utils.apidoc.entry;

import com.razorthink.utils.apidoc.core.RESTAPIScanner;

public class APISpecBuilderMain {

	public static void main( String[] args )
 throws Exception
	{
		if( args.length < 3 )
		{
			System.err.println("Error!\n\tMissing args: [project name] [package to scan] [destination dir for docs]");
			System.exit(0);
		}
		String[] pkgs = args[1].split(":");
		RESTAPIScanner apiScanner = new RESTAPIScanner(args[0]);
		apiScanner.scanPackagesAndGenerate(pkgs, args[2]);
	}
}
