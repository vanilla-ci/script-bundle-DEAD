package com.vanillaci.util;

import java.io.*;
import java.net.*;

/**
 * @author Joel Johnson
 */
public class ResourceUtil {
	public static File getResource(File file) throws FileNotFoundException {
		String path = file.getPath();
		URL resource = ResourceUtil.class.getClassLoader().getResource(path);
		if(resource != null) {
			String result = resource.getFile();
			return new File(result);
		}
		throw new FileNotFoundException("Resource with name not found: " + path);
	}
}
