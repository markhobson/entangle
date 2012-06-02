/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/TestValidators.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle.test;

import java.util.Arrays;
import java.util.Collection;

import org.entangle.Validator;
import org.jmock.Expectations;
import org.jmock.Mockery;


/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: TestValidators.java 100485 2012-04-16 08:28:19Z mark@IIZUKA.CO.UK $
 */
public final class TestValidators
{
	// types ------------------------------------------------------------------
	
	/**
	 * The exception that the {@link #throwException()} validator throws.
	 */
	public static final class Exception extends RuntimeException
	{
		// constructors -----------------------------------------------------------
		
		public Exception()
		{
			super();
		}
	}

	// constants --------------------------------------------------------------
	
	private static final String SOURCE_VALIDATOR_NAME = "sourceValidator";
	
	private static final String TARGET_VALIDATOR_NAME = "targetValidator";
	
	private static final Validator<Object, String> THROW_EXCEPTION = new Validator<Object, String>()
	{
		@Override
		public Collection<String> validate(Object object)
		{
			throw new Exception();
		}
	};

	// constructors -----------------------------------------------------------
	
	private TestValidators()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static Validator<Integer, String> validInteger()
	{
		return valid();
	}
	
	public static Validator<Number, String> validNumber()
	{
		return valid();
	}
	
	public static Validator<String, String> validString()
	{
		return valid();
	}
	
	public static <T> Validator<T, String> valid()
	{
		return new Validator<T, String>()
		{
			@Override
			public Collection<String> validate(T object)
			{
				return null;
			}
		};
	}
	
	public static Validator<Integer, String> invalidInteger(final String... violations)
	{
		return invalid(violations);
	}

	public static Validator<String, String> invalidString(final String... violations)
	{
		return invalid(violations);
	}
	
	public static <T> Validator<T, String> invalid(final String... violations)
	{
		return new Validator<T, String>()
		{
			@Override
			public Collection<String> validate(T object)
			{
				return Arrays.asList(violations);
			}
		};
	}
	
	public static <T> Validator<T, String> validIf(final T value, final String... violations)
	{
		return new Validator<T, String>()
		{
			@Override
			public Collection<String> validate(T object)
			{
				return safeEquals(value, object) ? null : Arrays.asList(violations);
			}
		};
	}
	
	public static <T> Validator<T, String> throwException()
	{
		// safe
		@SuppressWarnings("unchecked")
		Validator<T, String> validator = (Validator<T, String>) THROW_EXCEPTION;
		
		return validator;
	}
	
	public static <T, V> Validator<T, V> mockSourceValidator(Mockery mockery)
	{
		return mockValidator(mockery, SOURCE_VALIDATOR_NAME);
	}
	
	public static <T, V> Validator<T, V> mockSourceValidator(Mockery mockery, T expectedValue)
	{
		return mockValidator(mockery, SOURCE_VALIDATOR_NAME, expectedValue);
	}
	
	public static <T, V> Validator<T, V> mockTargetValidator(Mockery mockery)
	{
		return mockValidator(mockery, TARGET_VALIDATOR_NAME);
	}
	
	public static <T, V> Validator<T, V> mockTargetValidator(Mockery mockery, T expectedValue)
	{
		return mockValidator(mockery, TARGET_VALIDATOR_NAME, expectedValue);
	}
	
	// private methods --------------------------------------------------------
	
	private static <T, V> Validator<T, V> mockValidator(Mockery mockery, String name)
	{
		// cannot safely mock generic types
		@SuppressWarnings("unchecked")
		Validator<T, V> validator = mockery.mock(Validator.class, name);
		
		return validator;
	}
	
	private static <T, V> Validator<T, V> mockValidator(Mockery mockery, String name, final T expectedValue)
	{
		final Validator<T, V> validator = mockValidator(mockery, name);
		
		mockery.checking(new Expectations() { {
			oneOf(validator).validate(expectedValue); will(returnValue(null));
		} });
		
		return validator;
	}
	
	private static boolean safeEquals(Object x, Object y)
	{
		return (x == null) ? (y == null) : x.equals(y);
	}
}
