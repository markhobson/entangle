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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.entangle.DelegatingObservable;
import org.entangle.Observable;
import org.entangle.Observables;
import org.junit.Test;

/**
 * Tests {@code DelegatingObservable}.
 * 
 * @author Mark Hobson
 * @see DelegatingObservable
 */
public class DelegatingObservableTest
{
	// types ------------------------------------------------------------------
	
	private static class FakeDelegatingObservable<T> extends DelegatingObservable<T>
	{
		// constructors -----------------------------------------------------------
		
		public FakeDelegatingObservable()
		{
			super();
		}

		public FakeDelegatingObservable(Observable<T> delegate)
		{
			super(delegate);
		}
	}

	// tests ------------------------------------------------------------------
	
	@Test
	public void newDelegatingObservable()
	{
		DelegatingObservable<Object> observable = createDelegatingObservable();
		
		assertEquals(Observables.nullObservable(), observable.getDelegate());
	}

	@Test
	public void newDelegatingObservableWithDelegate()
	{
		Observable<Object> delegate = Observables.instance();
		DelegatingObservable<Object> observable = createDelegatingObservable(delegate);
		
		assertSame(delegate, observable.getDelegate());
	}
	
	@Test(expected = NullPointerException.class)
	public void newDelegatingObservableWithNullDelegate()
	{
		new FakeDelegatingObservable<Object>(null);
	}
	
	@Test
	public void getValue()
	{
		DelegatingObservable<String> observable = createDelegatingObservable(Observables.instance("x"));
		
		assertEquals("x", observable.getValue());
	}
	
	@Test
	public void setValue()
	{
		Observable<String> delegate = Observables.instance();
		DelegatingObservable<String> observable = createDelegatingObservable(delegate);
		
		observable.setValue("x");
		
		assertEquals("x", delegate.getValue());
	}
	
	@Test
	public void setDelegate()
	{
		DelegatingObservable<String> observable = createDelegatingObservable();
		Observable<String> delegate = Observables.instance();
		
		observable.setDelegate(delegate);
		
		assertSame(delegate, observable.getDelegate());
	}
	
	@Test(expected = NullPointerException.class)
	public void setDelegateWithNull()
	{
		DelegatingObservable<String> observable = createDelegatingObservable();
		
		observable.setDelegate(null);
	}
	
	// private methods --------------------------------------------------------
	
	private static <T> DelegatingObservable<T> createDelegatingObservable()
	{
		return new FakeDelegatingObservable<T>();
	}
	
	private static <T> DelegatingObservable<T> createDelegatingObservable(Observable<T> delegate)
	{
		return new FakeDelegatingObservable<T>(delegate);
	}
}
