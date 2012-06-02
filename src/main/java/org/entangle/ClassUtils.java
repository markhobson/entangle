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
 */
final class ClassUtils
{
	// constructors -----------------------------------------------------------
	
	private ClassUtils()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> Class<T> box(Class<T> type)
	{
		Class<T> boxedType;
		
		if (Boolean.TYPE.equals(type))
		{
			boxedType = (Class<T>) Boolean.class;
		}
		else if (Byte.TYPE.equals(type))
		{
			boxedType = (Class<T>) Byte.class;
		}
		else if (Short.TYPE.equals(type))
		{
			boxedType = (Class<T>) Short.class;
		}
		else if (Integer.TYPE.equals(type))
		{
			boxedType = (Class<T>) Integer.class;
		}
		else if (Long.TYPE.equals(type))
		{
			boxedType = (Class<T>) Long.class;
		}
		else if (Character.TYPE.equals(type))
		{
			boxedType = (Class<T>) Character.class;
		}
		else if (Float.TYPE.equals(type))
		{
			boxedType = (Class<T>) Float.class;
		}
		else if (Double.TYPE.equals(type))
		{
			boxedType = (Class<T>) Double.class;
		}
		else
		{
			boxedType = type;
		}
		
		return boxedType;
	}
}
