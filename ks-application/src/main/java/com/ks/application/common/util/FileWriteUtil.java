package com.ks.application.common.util;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriteUtil {

    public static void write(String filePath, String content) throws Exception {
    	try (BufferedWriter writer = Files.newBufferedWriter(
    	        Paths.get(filePath),
    	        StandardCharsets.UTF_8,
    	        StandardOpenOption.CREATE,
    	        StandardOpenOption.APPEND)) {

    	    writer.write(content);
    	    writer.newLine();   // always starts new line
    	}
    	
    }
}
