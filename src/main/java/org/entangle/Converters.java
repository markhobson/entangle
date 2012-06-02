/*
 * Copyright 2012 IIZUKA Software Technologies Ltd
 * 
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
