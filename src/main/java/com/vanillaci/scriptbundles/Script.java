package com.vanillaci.scriptbundles;

import com.vanillaci.exceptions.*;
import com.vanillaci.util.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

/**
 * @author Joel Johnson
 */
public class Script implements Serializable {
	@NotNull
	private final File bundleDirectory;

	@NotNull
	private final Manifest manifest;

	@NotNull
	public static Script forDirectory(@NotNull File bundleDirectory) throws IOException {
		if(!bundleDirectory.exists()) {
			throw new FileNotFoundException("Bundle directory doesn't exist: " + bundleDirectory.getAbsolutePath());
		}
		if(!bundleDirectory.isDirectory()) {
			throw new FileNotFoundException("Bundle directory must be a directory: " + bundleDirectory.getAbsolutePath());
		}

		File manifestFile = new File(bundleDirectory, "manifest.json");
		if(!manifestFile.exists() || !manifestFile.isFile()) {
			throw new InvalidScriptBundleException("Bundle directory must contain a manifest.json file at the given path: " + manifestFile.getAbsolutePath() + ". See documentation for more information.");
		}

		Manifest manifest = JsonUtil.parseJson(manifestFile, Manifest.TYPE_REFERENCE);
		return new Script(bundleDirectory, manifest);
	}

	private Script(@NotNull File bundleDirectory, @NotNull Manifest manifest) {
		this.bundleDirectory = bundleDirectory;
		this.manifest = manifest;
	}

	public Process execute(@NotNull File workingDirectory, @NotNull Environment environment, @Nullable File outputFile) throws IOException, InterruptedException {
		String mainFile = manifest.getMain();
		if(OS.getOS() == OS.WINDOWS) {
			mainFile = mainFile + ".bat";
		}
		File executable = new File(bundleDirectory, mainFile);

		// Try to make sure the script file is executable.
		//noinspection ResultOfMethodCallIgnored
		executable.setExecutable(true);

		ProcessBuilder processBuilder = new ProcessBuilder(executable.getPath())
			.directory(workingDirectory);

		if(outputFile == null) {
			processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		} else {
			processBuilder.redirectOutput(outputFile);
		}

		Map<String,String> processEnvironment = processBuilder.environment();
		processEnvironment.put("BUNDLE_DIR", bundleDirectory.getAbsolutePath());
		processEnvironment.putAll(environment.toMap());

		return processBuilder.start();
	}

	@NotNull
	public File getBundleDirectory() {
		return bundleDirectory;
	}

	@NotNull
	public Manifest getManifest() {
		return manifest;
	}
}
