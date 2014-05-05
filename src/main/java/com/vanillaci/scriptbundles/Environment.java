package com.vanillaci.scriptbundles;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * Used for setting custom environment variables for a script bundle to use.
 *
 * @author Joel Johnson
 */
public class Environment {
	private final Map<String, String> environment;

	public Environment() {
		environment = new HashMap<>();
	}

	public Environment(@NotNull String... pairs) {
		this();
		if(pairs.length % 2 != 0) {
			throw new IllegalArgumentException("pairs must have an even number of parameters");
		}
		for(int i = 0; i + 1 < pairs.length; i += 2) {
			environment.put(pairs[i], pairs[i+1]);
		}
	}

	public void addEnvironmentVariable(@NotNull String name, @NotNull String value) {
		environment.put(name, value);
	}

	public void removeEnvironmentVariable(@NotNull String name) {
		environment.remove(name);
	}

	/**
	 * Returns an immutable copy of the environment variables that have been set.
	 */
	@NotNull
	public Map<String, String> toMap() {
		return ImmutableMap.copyOf(environment);
	}
}
