package com.vanillaci.util;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.*;

import java.io.*;

/**
 * @author Joel Johnson
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T parseJson(File jsonFile, TypeReference<T> typeReference) throws IOException {
		return mapper.readValue(jsonFile, typeReference);
	}
}
