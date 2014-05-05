package com.vanillaci.scriptbundles;

import org.junit.*;

import java.util.*;

/**
 * @author Joel Johnson
 */
public class EnvironmentTest {
	@Test
	public void testDefault() {
		Environment environment = new Environment("val1", "1", "val2", "2");
		Map<String,String> map = environment.toMap();

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("1", map.get("val1"));
		Assert.assertEquals("2", map.get("val2"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDefault_badInput() {
		new Environment("val1", "1", "noMatch");
	}

	@Test
	public void testAdd() {
		Environment environment = new Environment();
		environment.addEnvironmentVariable("Blah", "some value");

		Map<String,String> map = environment.toMap();

		Assert.assertEquals(1, map.size());
		Assert.assertEquals("some value", map.get("Blah"));
	}

	@Test
	public void testRemove() {
		Environment environment = new Environment("blah", "remove me");
		environment.removeEnvironmentVariable("blah");

		Map<String,String> map = environment.toMap();
		Assert.assertEquals(0, map.size());
	}
}
