/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/TestConverters.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle.test;

import org.entangle.Converter;
import org.jmock.Expectations;
import org.jmock.Mockery;


/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: TestConverters.java 100485 2012-04-16 08:28:19Z mark@IIZUKA.CO.UK $
 */
public final class TestConverters
{
	// types ------------------------------------------------------------------
	
	/**
	 * The exception that the {@link #throwException()} converter throws.
	 */
	public static final class Exception extends RuntimeException
	{
		// constructors -----------------------------------------------------------
		
		public Exception()
		{
			super();
		}
	}
	
	private abstract static class UnidirectionalConverter<S, T> implements Converter<S, T>
	{
		// Converter methods ------------------------------------------------------

		/**
		 * {@inheritDoc}
		 */
		@Override
		public S unconvert(T target)
		{
			throw new UnsupportedOperationException();
		}
	}

	private abstract static class IdentityConverter<T> implements Converter<T, T>
	{
		// Converter methods ------------------------------------------------------

		/**
		 * {@inheritDoc}
		 */
		@Override
		public final T unconvert(T target)
		{
			return convert(target);
		}
	}
	
	private static class ToStringConverter<T> extends UnidirectionalConverter<T, String>
	{
		// Converter methods ------------------------------------------------------

		/**
		 * {@inheritDoc}
		 */
		@Override
		public final String convert(T target)
		{
			return String.valueOf(target);
		}
	}
	
	// constants --------------------------------------------------------------
	
	private static final Converter<Object, Object> IDENTITY = new IdentityConverter<Object>()
	{
		@Override
		public Object convert(Object target)
		{
			return target;
		}
	};
	
	private static final Converter<Object, String> TO_STRING = new ToStringConverter<Object>();
	
	private static final Converter<Object, CharSequence> TO_CHAR_SEQUENCE
		= new UnidirectionalConverter<Object, CharSequence>()
	{
		@Override
		public CharSequence convert(Object target)
		{
			return String.valueOf(target);
		}
	};
	
	private static final Converter<Integer, String> INTEGER_TO_STRING = new ToStringConverter<Integer>()
	{
		@Override
		public Integer unconvert(String source)
		{
			return (source != null) ? Integer.valueOf(source) : null;
		}
	};
		
	private static final Converter<Object, Object> THROW_EXCEPTION = new IdentityConverter<Object>()
	{
		@Override
		public Object convert(Object target)
		{
			throw new Exception();
		}
	};
	
	// constructors -----------------------------------------------------------
	
	private TestConverters()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static Converter<String, String> identityString()
	{
		return identity();
	}
	
	public static Converter<Integer, String> integerToString()
	{
		return INTEGER_TO_STRING;
	}
	
	public static Converter<Integer, CharSequence> integerToCharSequence()
	{
		return toCharSequence();
	}
	
	public static Converter<Number, String> numberToString()
	{
		return toStringConverter();
	}
	
	public static <T> Converter<T, String> toStringConverter()
	{
		// safe
		@SuppressWarnings("unchecked")
		Converter<T, String> converter = (Converter<T, String>) TO_STRING;
		
		return converter;
	}
	
	public static <T> Converter<T, CharSequence> toCharSequence()
	{
		// safe
		@SuppressWarnings("unchecked")
		Converter<T, CharSequence> converter = (Converter<T, CharSequence>) TO_CHAR_SEQUENCE;
		
		return converter;
	}
	
	public static Converter<String, Integer> stringToInteger()
	{
		return inverse(integerToString());
	}
	
	public static <S, T> Converter<S, T> throwException()
	{
		// safe
		@SuppressWarnings("unchecked")
		Converter<S, T> converter = (Converter<S, T>) THROW_EXCEPTION;
		
		return converter;
	}
	
	public static Converter<String, String> mockStringConverter(Mockery mockery)
	{
		return mockConverter(mockery);
	}
	
	public static <S, T> Converter<S, T> mockConverter(Mockery mockery)
	{
		// cannot safe mock generic types
		@SuppressWarnings("unchecked")
		Converter<S, T> converter = mockery.mock(Converter.class);
		
		return converter;
	}
	
	public static <S, T> Converter<S, T> mockConverter(Mockery mockery, final S source, final T target)
	{
		final Converter<S, T> converter = mockConverter(mockery);
		
		mockery.checking(new Expectations() { {
			oneOf(converter).convert(source); will(returnValue(target));
		} });
		
		return converter;
	}
	
	public static <S, T> Converter<S, T> mockUnconverter(Mockery mockery, final T target, final S source)
	{
		final Converter<S, T> converter = mockConverter(mockery);
		
		mockery.checking(new Expectations() { {
			oneOf(converter).unconvert(target); will(returnValue(source));
		} });
		
		return converter;
	}
	
	// private methods --------------------------------------------------------
	
	private static <T> Converter<T, T> identity()
	{
		// safe
		@SuppressWarnings("unchecked")
		Converter<T, T> converter = (Converter<T, T>) IDENTITY;
		
		return converter;
	}
	
	private static <S, T> Converter<T, S> inverse(final Converter<S, T> converter)
	{
		return new Converter<T, S>()
		{
			@Override
			public S convert(T target)
			{
				return converter.unconvert(target);
			}
			
			@Override
			public T unconvert(S source)
			{
				return converter.convert(source);
			}
		};
	}
}
