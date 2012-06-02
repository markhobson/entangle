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
package org.entangle;

import static org.entangle.ObservableAssert.assertInstanceObservable;
import static org.entangle.test.TestObservableListeners.mockObservableListener;
import static org.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static org.junit.Assert.assertEquals;

import org.entangle.InstanceObservable;
import org.entangle.ObservableListener;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code InstanceObservable}.
 * 
 * @author Mark Hobson
 * @see InstanceObservable
 */
@RunWith(JMock.class)
public class InstanceObservableTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();

	// tests ------------------------------------------------------------------
	
	@Test
	public void newInstanceObservable()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		
		assertInstanceObservable(null, observable);
	}
	
	@Test
	public void newInstanceObservableWithValue()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>("a");
		
		assertInstanceObservable("a", observable);
	}
	
	@Test
	public void newInstanceObservableWithNull()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>(null);
		
		assertInstanceObservable(null, observable);
	}
	
	@Test
	public void setValue()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		observable.setValue("a");
		
		assertEquals("a", observable.getValue());
	}

	@Test
	public void addObservableListenerReceivesEventWhenPropertyChanged()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		
		observable.addObservableListener(mockObservableListenerWithValueChanged(mockery, observable, null, "a"));
		
		observable.setValue("a");
	}
	
	@Test
	public void addObservableListenerDoesNotReceiveEventWhenPropertyChangedAndValueEqual()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>("a");
		
		observable.addObservableListener(mockObservableListener(mockery, String.class));
		
		observable.setValue("a");
	}
	
	@Test(expected = NullPointerException.class)
	public void addObservableListenerWithNull()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		
		observable.addObservableListener(null);
	}
	
	@Test
	public void removeObservableListenerNoLongerReceivesEventWhenPropertyChanged()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		observable.addObservableListener(listener);
		observable.removeObservableListener(listener);
		
		observable.setValue("a");
	}

	@Test(expected = NullPointerException.class)
	public void removeObservableListenerWithNull()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>();
		
		observable.removeObservableListener(null);
	}
	
	@Test
	public void toStringTest()
	{
		InstanceObservable<String> observable = new InstanceObservable<String>("a");
		
		assertEquals("org.entangle.InstanceObservable[value=a]", observable.toString());
	}
}
