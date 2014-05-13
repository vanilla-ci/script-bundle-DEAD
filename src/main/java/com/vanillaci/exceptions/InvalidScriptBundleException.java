package com.vanillaci.exceptions;

import java.io.*;

/**
 * @author Joel Johnson
 */
public class InvalidScriptBundleException extends IOException {
	public InvalidScriptBundleException(String message) {
		super(message);
	}
}
