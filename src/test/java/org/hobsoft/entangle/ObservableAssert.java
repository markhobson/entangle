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
import static org.junit.Assert.assertTrue;

import org.hobsoft.entangle.BeanObservable;
import org.hobsoft.entangle.BeanObservablesDecorator;
import org.hobsoft.entangle.InstanceObservable;
import org.hobsoft.entangle.NullObservable;
import org.hobsoft.entangle.Observable;
import org.hobsoft.entangle.PropertyObservable;

/**
 * Provides custom assertions for {@code Observable} implementations.
 * 
 * @author Mark Hobson
 * @see Observable
 */
public final class ObservableAssert
{
	// NOTE: this class is not within the test subpackage since Observable implementations are package private
	
	// constructors -----------------------------------------------------------
	
	private ObservableAssert()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static void assertNullObservable(Observable<?> actual)
	{
		assertTrue("Expected NullObservable", actual instanceof NullObservable);
	}
	
	public static void assertInstanceObservable(Object expectedInstance, Observable<?> actual)
	{
		assertTrue("Expected InstanceObservable", actual instanceof InstanceObservable);
		assertEquals("Instance", expectedInstance, actual.getValue());
	}
	
	public static void assertBeanObservable(Object expectedBean, Observable<?> actual)
	{
		assertTrue("Expected BeanObservable", actual instanceof BeanObservable);
		assertEquals("Bean", expectedBean, actual.getValue());
	}
	
	public static void assertPropertyObservable(Object expectedBean, String expectedPropertyName,
		Class<?> expectedPropertyType, Observable<?> actual)
	{
		assertTrue("Expected PropertyObservable", actual instanceof PropertyObservable);
		
		PropertyObservable<?> narrowedActual = (PropertyObservable<?>) actual;
		
		assertEquals("Bean", expectedBean, narrowedActual.getBean());
		assertEquals("Property name", expectedPropertyName, narrowedActual.getPropertyName());
		assertEquals("Property type", expectedPropertyType, narrowedActual.getPropertyType());
	}
	
	public static void assertDecoratedBeanObservable(Object expectedBean, Observable<?> actual)
	{
		assertTrue("Expected BeanObservablesDecorator", actual instanceof BeanObservablesDecorator);
		
		BeanObservablesDecorator<?> narrowedActual = (BeanObservablesDecorator<?>) actual;
		Observable<?> actualObservable = narrowedActual.getDelegate();
		
		assertBeanObservable(expectedBean, actualObservable);
	}
	
	public static void assertDecoratedPropertyObservable(Object expectedBean, String expectedPropertyName,
		Class<?> expectedPropertyType, Observable<?> actual)
	{
		assertTrue("Expected BeanObservablesDecorator", actual instanceof BeanObservablesDecorator);
		
		BeanObservablesDecorator<?> narrowedActual = (BeanObservablesDecorator<?>) actual;
		Observable<?> actualObservable = narrowedActual.getDelegate();
		
		assertPropertyObservable(expectedBean, expectedPropertyName, expectedPropertyType, actualObservable);
	}
}
