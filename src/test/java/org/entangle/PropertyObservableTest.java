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

import static org.entangle.ObservableAssert.assertPropertyObservable;
import static org.entangle.test.TestObservableListeners.mockObservableListener;
import static org.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.entangle.ObservableListener;
import org.entangle.PropertyObservable;
import org.entangle.test.FakeBean;
import org.entangle.test.FakeBoundBean;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code PropertyObservable}.
 * 
 * @author Mark Hobson
 * @version $Id: PropertyObservableTest.java 97741 2012-01-13 16:01:26Z mark@IIZUKA.CO.UK $
 * @see PropertyObservable
 */
@RunWith(JMock.class)
public class PropertyObservableTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();
	
	private FakeBean bean;
	
	private FakeBoundBean boundBean;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public void setUp()
	{
		bean = new FakeBean();
		boundBean = new FakeBoundBean();
	}

	// tests ------------------------------------------------------------------
	
	@Test(expected = NullPointerException.class)
	public void newPropertyObservableWithNullBean()
	{
		new PropertyObservable<String>(null, "name", String.class);
	}
	
	@Test
	public void newPropertyObservableWithUnboundBean()
	{
		PropertyObservable<String> observable = new PropertyObservable<String>(bean, "name", String.class);
		
		assertPropertyObservable(bean, "name", String.class, observable);
	}
	
	@Test(expected = NullPointerException.class)
	public void newPropertyObservableWithNullPropertyName()
	{
		new PropertyObservable<String>(bean, null, String.class);
	}
	
	@Test(expected = NullPointerException.class)
	public void newPropertyObservableWithNullPropertyType()
	{
		new PropertyObservable<String>(bean, "name", null);
	}
	
	@Test
	public void newPropertyObservableWithPrimitivePropertyType()
	{
		PropertyObservable<Integer> observable = new PropertyObservable<Integer>(bean, "age", int.class);
		
		assertPropertyObservable(bean, "age", int.class, observable);
	}
	
	// TODO: support?
	@Ignore
	@Test
	public void newPropertyObservableWithBoxedPropertyType()
	{
		PropertyObservable<Integer> observable = new PropertyObservable<Integer>(bean, "age", Integer.class);
		
		assertPropertyObservable(bean, "age", Integer.class, observable);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void newPropertyObservableWithAssignablePropertyType()
	{
		new PropertyObservable<Object>(bean, "name", Object.class);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void newPropertyObservableWithUnassignablePropertyType()
	{
		new PropertyObservable<Integer>(bean, "name", Integer.class);
	}
	
	@Test
	public void getValue()
	{
		bean.setName("a");
		
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);
		
		assertEquals("a", observable.getValue());
	}
	
	@Test
	public void getValueWithPrimitive()
	{
		bean.setAge(123);
		
		PropertyObservable<Integer> observable = PropertyObservable.get(bean, "age", int.class);
		
		assertEquals(Integer.valueOf(123), observable.getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getValueWithWriteOnly()
	{
		bean.setName("a");
		
		PropertyObservable<String> observable = PropertyObservable.get(bean, "writeOnlyName", String.class);
		
		observable.getValue();
	}
	
	@Test
	public void setValue()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);
		observable.setValue("a");
		
		assertEquals("a", bean.getName());
	}
	
	@Test
	public void setValueWithNull()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);
		observable.setValue(null);
		
		assertNull(bean.getName());
	}
	
	@Test
	public void setValueWithPrimitive()
	{
		PropertyObservable<Integer> observable = PropertyObservable.get(bean, "age", int.class);
		observable.setValue(123);
		
		assertEquals(123, bean.getAge());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setValueWithPrimitiveWhenNull()
	{
		PropertyObservable<Integer> observable = PropertyObservable.get(bean, "age", int.class);
		observable.setValue(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setValueWithReadOnly()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "readOnlyName", String.class);
		observable.setValue("a");
	}
	
	@Test
	public void addObservableListenerReceivesEventWhenPropertyChanged()
	{
		PropertyObservable<String> observable = PropertyObservable.get(boundBean, "name", String.class);
		
		observable.addObservableListener(mockObservableListenerWithValueChanged(mockery, observable, null, "a"));
		
		boundBean.setName("a");
	}
	
	@Test
	public void addObservableListenerReceivesEventWhenPropertyChangedWithPrimitivePropertyType()
	{
		PropertyObservable<Integer> observable = PropertyObservable.get(boundBean, "age", int.class);
		
		observable.addObservableListener(mockObservableListenerWithValueChanged(mockery, observable, 0, 123));
		
		boundBean.setAge(123);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void addObservableListenerWhenBeanUnbound()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);
		
		observable.addObservableListener(mockObservableListener(mockery, String.class));
	}
	
	@Test(expected = NullPointerException.class)
	public void addObservableListenerWithNull()
	{
		PropertyObservable<String> observable = PropertyObservable.get(boundBean, "name", String.class);
		
		observable.addObservableListener(null);
	}
	
	@Test
	public void removeObservableListenerNoLongerReceivesEventWhenPropertyChanged()
	{
		PropertyObservable<String> observable = PropertyObservable.get(boundBean, "name", String.class);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		observable.addObservableListener(listener);
		observable.removeObservableListener(listener);
		
		boundBean.setName("a");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeObservableListenerWhenBeanUnbound()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);
		
		observable.removeObservableListener(mockObservableListener(mockery, String.class));
	}
	
	@Test(expected = NullPointerException.class)
	public void removeObservableListenerWithNull()
	{
		PropertyObservable<String> observable = PropertyObservable.get(boundBean, "name", String.class);
		
		observable.removeObservableListener(null);
	}
	
	@Test
	public void toStringTest()
	{
		PropertyObservable<String> observable = PropertyObservable.get(bean, "name", String.class);

		String expected = "org.entangle.PropertyObservable[bean=" + bean + ",propertyName=name]";
		assertEquals(expected, observable.toString());
	}
}
