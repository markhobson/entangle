/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/ClassUtils.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ClassUtils.java 89097 2011-06-20 10:04:32Z mark@IIZUKA.CO.UK $
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
