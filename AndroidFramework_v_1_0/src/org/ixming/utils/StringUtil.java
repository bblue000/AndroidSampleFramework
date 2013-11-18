package org.ixming.utils;

/**
 * utilities of string
 * @author Yin Yong
 * @version 1.0
 */
public class StringUtil {

	/**
	 * no instance needed
	 */
	private StringUtil() { /*no instance*/ }
	
	/**
	 * null-value String
	 */
	private static final String NULL = "null";
	
	/**
	 * check this target string is NULL or empty; <br/>
	 * if <code>checkContentNull</code> is set true,
	 * it'll also check if it is a "null" string, ignoring Case.
	 * @param target target string to check
	 * @param checkContentNull if is set true,
	 * it'll also check if it is a "null" string, ignoring Case.
	 */
	public static boolean isEmpty(String target, boolean checkContentNull) {
		// check if target is null, or a len == 0 string
		return ((null == target)
				|| (target.length() == 0)
				|| (checkContentNull && NULL.equalsIgnoreCase(target)));
	}
	
	/**
	 * check this target string is null or empty;
	 * @param target target string to check
	 * @see #isEmpty(String, false)
	 */
	public static boolean isEmpty(String target) {
		return isEmpty(target, false);
	}
	
}
