package com.vanillaci.scriptbundles;

import com.vanillaci.util.*;
import org.junit.*;

import java.io.*;
import java.util.*;

/**
 * @author Joel Johnson
 */
public class ScriptTest {
	@Test
	public void testExecuteBundle1() throws IOException, InterruptedException {
		File bundleDirectory = ResourceUtil.getResource(new File("bundle1"));
		Script script = Script.forDirectory(bundleDirectory);

		Assert.assertEquals("bundle1", script.getManifest().getName());
		Assert.assertEquals("script", script.getManifest().getMain());
		Assert.assertEquals("1.0", script.getManifest().getVersion());

		File tempFile = File.createTempFile("test", "tmp");
		tempFile.deleteOnExit();

		int exitCode = script.execute(new File("."), new Environment(), tempFile);

		Assert.assertEquals(0, exitCode);

		Assert.assertTrue(tempFile.exists());
		try(Scanner scanner = new Scanner(tempFile)) {
			Assert.assertTrue(scanner.hasNextLine());
			Assert.assertEquals("hello from bundle1", scanner.nextLine().trim());
		}
	}

	@Test
	public void testExecuteBundleWithVariables() throws IOException, InterruptedException {
		File bundleDirectory = ResourceUtil.getResource(new File("bundleWithVariables"));
		Script script = Script.forDirectory(bundleDirectory);
		Assert.assertEquals(bundleDirectory, script.getBundleDirectory());

		Assert.assertEquals("bundleWithVariables", script.getManifest().getName());
		Assert.assertEquals("variableScript", script.getManifest().getMain());
		Assert.assertEquals("1.1", script.getManifest().getVersion());

		File tempFile = File.createTempFile("test", "tmp");
		tempFile.deleteOnExit();

		Environment environment = new Environment();
		environment.addEnvironmentVariable("SOME_EXIT_CODE", "137");
		int exitCode = script.execute(new File("."), environment, tempFile);

		Assert.assertTrue(tempFile.exists());
		try(Scanner scanner = new Scanner(tempFile)) {
			Assert.assertTrue(scanner.hasNextLine());
			Assert.assertEquals(bundleDirectory.getAbsolutePath(), scanner.nextLine().trim());

		}

		Assert.assertEquals(137, exitCode);
	}
}
