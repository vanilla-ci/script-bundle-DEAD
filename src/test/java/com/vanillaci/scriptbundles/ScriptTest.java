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

		File tempFile = File.createTempFile("test", "tmp");
		tempFile.deleteOnExit();

		script.execute(new File("."), new Environment(), tempFile);

		Assert.assertTrue(tempFile.exists());
		try(Scanner scanner = new Scanner(tempFile)) {
			Assert.assertTrue(scanner.hasNextLine());
			Assert.assertEquals("hello from bundle1", scanner.nextLine().trim());
		}
	}
}
