package com.vanillaci.scriptbundles;

import org.codehaus.jackson.annotate.*;
import org.codehaus.jackson.type.*;
import org.jetbrains.annotations.*;

import java.io.*;

/**
 * @author Joel Johnson
 */
public class Manifest implements Serializable {
	public static final TypeReference<Manifest> TYPE_REFERENCE = new TypeReference<Manifest>() {};

	@NotNull private final String name;
	@NotNull private final String version;
	@NotNull private final String main;

	@JsonCreator
	public Manifest(
		@JsonProperty("main") @NotNull String main,
		@JsonProperty("version") @NotNull String version,
		@JsonProperty("name") @NotNull String name
	) {
		this.main = main;
		this.version = version;
		this.name = name;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public String getVersion() {
		return version;
	}

	@NotNull
	public String getMain() {
		return main;
	}
}
