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

import static org.entangle.test.TestObservableListeners.mockObservableListener;
import static org.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static org.junit.Assert.assertEquals;

import org.entangle.HierarchicalCompositeObservable;
import org.entangle.Observable;
import org.entangle.ObservableListener;
import org.entangle.Observables;
import org.entangle.Observables.ObservableFactory;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code HierarchicalCompositeObservable}.
 * 
 * @author Mark Hobson
 * @see HierarchicalCompositeObservable
 */
@RunWith(JMock.class)
public class HierarchicalCompositeObservableTest
{
	// TODO: test parent-child interaction
	
	// fields -----------------------------------------------------------------
	
	private final Mockery mockery = new JUnit4Mockery();
	
	private Observable<String> parent;
	
	private Observable<String> child;
	
	private ObservableFactory<String, String> childFactory;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public void setUp()
	{
		parent = Observables.instance();
		child = Observables.instance();
		
		childFactory = constantFactory(child);
	}

	// tests ------------------------------------------------------------------
	
	@Test
	public void getValue()
	{
		child.setValue("a");
		
		HierarchicalCompositeObservable<String, String> composite = compose(parent, childFactory);
		
		assertEquals("a", composite.getValue());
	}
	
	@Test
	public void setValue()
	{
		HierarchicalCompositeObservable<String, String> composite = compose(parent, childFactory);
		
		composite.setValue("a");
		
		assertEquals("a", child.getValue());
	}
	
	@Test
	public void addObservableListener()
	{
		HierarchicalCompositeObservable<String, String> composite = compose(parent, childFactory);
		
		// TODO: expect composite as source?
		composite.addObservableListener(mockObservableListenerWithValueChanged(mockery, child, null, "a"));
		
		composite.setValue("a");
	}
	
	@Test
	public void removeObservableListener()
	{
		HierarchicalCompositeObservable<String, String> composite = compose(parent, childFactory);

		ObservableListener<String> listener = mockObservableListener(mockery);
		composite.addObservableListener(listener);
		composite.removeObservableListener(listener);
		
		composite.setValue("a");
	}
	
	@Test
	public void toStringTest()
	{
		HierarchicalCompositeObservable<String, String> composite = compose(parent, childFactory);

		String expected = "org.entangle.HierarchicalCompositeObservable["
			+ "parent=" + parent + ", "
			+ "childFactory=" + childFactory
			+ "]";
		
		assertEquals(expected, composite.toString());
	}
	
	// private methods --------------------------------------------------------
	
	private static <T, U> HierarchicalCompositeObservable<T, U> compose(Observable<T> parent,
		ObservableFactory<T, U> childFactory)
	{
		return new HierarchicalCompositeObservable<T, U>(parent, childFactory);
	}
	
	private static <T, U> ObservableFactory<T, U> constantFactory(final Observable<U> observable)
	{
		return new ObservableFactory<T, U>()
		{
			@Override
			public Observable<U> createObservable(T value)
			{
				return observable;
			}
		};
	}
}
