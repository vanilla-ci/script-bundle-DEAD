package com.vanillaci.scriptbundles;

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

	public static Script forDirectory(File bundleDirectory) throws IOException {
		File manifestFile = new File(bundleDirectory, "manifest.json");
		Manifest manifest = JsonUtil.parseJson(manifestFile, Manifest.TYPE_REFERENCE);
		return new Script(bundleDirectory, manifest);
	}

	private Script(@NotNull File bundleDirectory, @NotNull Manifest manifest) {
		this.bundleDirectory = bundleDirectory;
		this.manifest = manifest;
	}

	public int execute(@NotNull File workingDirectory, @NotNull Environment environment) throws IOException, InterruptedException {
		return execute(workingDirectory, environment, null);
	}

	public int execute(@NotNull File workingDirectory, @NotNull Environment environment, @Nullable File outputFile) throws IOException, InterruptedException {
		String mainFile = manifest.getMain();
		if(OS.getOS() == OS.WINDOWS) {
			mainFile = mainFile + ".bat";
		}
		File executable = new File(bundleDirectory, mainFile);

		// Try to make sure the script file is executable.
		//noinspection ResultOfMethodCallIgnored
		executable.setExecutable(true);

		ProcessBuilder processBuilder = new ProcessBuilder(executable.getPath())
			.redirectErrorStream(true)
			.directory(workingDirectory);

		if(outputFile == null) {
			processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		} else {
			processBuilder.redirectOutput(outputFile);
		}

		Map<String,String> processEnvironment = processBuilder.environment();
		processEnvironment.put("BUNDLE_DIR", bundleDirectory.getAbsolutePath());
		processEnvironment.putAll(environment.toMap());

		Process process = processBuilder.start();
		try {
			return process.waitFor();
		} catch (InterruptedException e) {
			process.destroy();
			throw e;
		}
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
