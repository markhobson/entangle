/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/ClassUtilsTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Tests {@code ClassUtils}.
 * 
 * @author Mark Hobson
 * @version $Id: ClassUtilsTest.java 89097 2011-06-20 10:04:32Z mark@IIZUKA.CO.UK $
 * @see ClassUtils
 */
public class ClassUtilsTest
{
	// tests ------------------------------------------------------------------
	
	@Test
	public void boxWithBooleanPrimitive()
	{
		assertEquals(Boolean.class, ClassUtils.box(Boolean.TYPE));
	}
	
	@Test
	public void boxWithBytePrimitive()
	{
		assertEquals(Byte.class, ClassUtils.box(Byte.TYPE));
	}
	
	@Test
	public void boxWithShortPrimitive()
	{
		assertEquals(Short.class, ClassUtils.box(Short.TYPE));
	}
	
	@Test
	public void boxWithIntPrimitive()
	{
		assertEquals(Integer.class, ClassUtils.box(Integer.TYPE));
	}
	
	@Test
	public void boxWithLongPrimitive()
	{
		assertEquals(Long.class, ClassUtils.box(Long.TYPE));
	}
	
	@Test
	public void boxWithCharPrimitive()
	{
		assertEquals(Character.class, ClassUtils.box(Character.TYPE));
	}
	
	@Test
	public void boxWithFloatPrimitive()
	{
		assertEquals(Float.class, ClassUtils.box(Float.TYPE));
	}
	
	@Test
	public void boxWithDoublePrimitive()
	{
		assertEquals(Double.class, ClassUtils.box(Double.TYPE));
	}
	
	@Test
	public void boxWithReferenceType()
	{
		assertEquals(Object.class, ClassUtils.box(Object.class));
	}
	
	@Test
	public void boxWithNull()
	{
		assertNull(ClassUtils.box(null));
	}
}
