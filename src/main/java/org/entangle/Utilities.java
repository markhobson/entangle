/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Utilities.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Utilities.java 97740 2012-01-13 15:52:19Z mark@IIZUKA.CO.UK $
 */
final class Utilities
{
	// constructors -----------------------------------------------------------
	
	private Utilities()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> T checkNotNull(T object, String message)
	{
		if (object == null)
		{
			throw new NullPointerException(message);
		}
		
		return object;
	}
	
	public static void checkArgument(boolean expression, String message)
	{
		if (!expression)
		{
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void checkArgument(boolean expression, String message, Object... arguments)
	{
		if (!expression)
		{
			throw new IllegalArgumentException(String.format(message, arguments));
		}
	}

	public static void checkState(boolean expression, String message)
	{
		if (!expression)
		{
			throw new IllegalStateException(message);
		}
	}
	
	public static <T> T firstNonNull(T x, T y)
	{
		return firstNonNull(x, y, "");
	}
	
	public static <T> T firstNonNull(T x, T y, String message)
	{
		return (x != null) ? x : checkNotNull(y, message);
	}
	
	public static int safeHashCode(Object x)
	{
		return (x != null) ? x.hashCode() : 0;
	}
	
	public static boolean safeEquals(Object x, Object y)
	{
		return (x == null) ? (y == null) : x.equals(y);
	}
}
