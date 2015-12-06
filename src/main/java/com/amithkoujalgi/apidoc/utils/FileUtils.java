package com.amithkoujalgi.apidoc.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
	public static void writeFile(String filePath, String content) throws IOException {
		File f = new File(filePath);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		FileWriter fw = new FileWriter(f);
		fw.write(content);
		fw.close();
	}
}
