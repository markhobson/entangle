/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/ObservableAssert.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.entangle.BeanObservable;
import org.entangle.BeanObservablesDecorator;
import org.entangle.InstanceObservable;
import org.entangle.NullObservable;
import org.entangle.Observable;
import org.entangle.PropertyObservable;

/**
 * Provides custom assertions for {@code Observable} implementations.
 * 
 * @author Mark Hobson
 * @version $Id: ObservableAssert.java 97776 2012-01-16 11:32:07Z mark@IIZUKA.CO.UK $
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
