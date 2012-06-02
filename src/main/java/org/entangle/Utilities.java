/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.entangle;

/**
 * 
 * 
 * @author Mark Hobson
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
