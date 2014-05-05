package com.vanillaci.util;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.*;
import org.jetbrains.annotations.*;

import java.io.*;

/**
 * @author Joel Johnson
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T parseJson(@NotNull File jsonFile, @NotNull TypeReference<T> typeReference) throws IOException {
		return mapper.readValue(jsonFile, typeReference);
	}

	public static <T> void serialize(T object, OutputStream outputStream) throws IOException {
		mapper.writeValue(outputStream, object);
	}
}
