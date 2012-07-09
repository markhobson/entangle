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
package org.hobsoft.entangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.hobsoft.entangle.ClassUtils;
import org.junit.Test;

/**
 * Tests {@code ClassUtils}.
 * 
 * @author Mark Hobson
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
