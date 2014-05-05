package com.vanillaci.util;

import org.apache.commons.lang.*;

/**
 * @author Joel Johnson
 */
public enum OS {
	WINDOWS,
	OTHER;

	public static OS getOS() {
		return SystemUtils.IS_OS_WINDOWS ? WINDOWS : OTHER;
	}
}
