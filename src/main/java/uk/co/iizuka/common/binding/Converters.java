/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Converters.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: Converters.java 97382 2011-12-28 20:21:54Z mark@IIZUKA.CO.UK $
 */
final class Converters
{
	// constants --------------------------------------------------------------
	
	private static final Converter<Object, Object> IDENTITY = new Converter<Object, Object>()
	{
		@Override
		public Object convert(Object object)
		{
			return object;
		}

		@Override
		public Object unconvert(Object object)
		{
			return object;
		}
	};

	// constructors -----------------------------------------------------------
	
	private Converters()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> Converter<T, T> identity()
	{
		@SuppressWarnings("unchecked")
		Converter<T, T> identity = (Converter<T, T>) IDENTITY;
		
		return identity;
	}
}
