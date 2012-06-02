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
package org.entangle.test;

import org.entangle.Observable;
import org.entangle.Observables;
import org.jmock.Expectations;
import org.jmock.Mockery;

/**
 * 
 * 
 * @author Mark Hobson
 */
public final class TestObservables
{
	// constants --------------------------------------------------------------
	
	private static final String SOURCE_NAME = "source";
	
	private static final String TARGET_NAME = "target";

	// constructors -----------------------------------------------------------
	
	private TestObservables()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> Observable<T> observable()
	{
		return Observables.instance();
	}
	
	public static Observable<String> observableString()
	{
		return TestObservables.<String>observable();
	}
	
	public static Observable<String> mockStringSource(Mockery mockery)
	{
		return mockSource(mockery);
	}
	
	public static <T> Observable<T> mockSource(Mockery mockery)
	{
		return mockObservable(mockery, SOURCE_NAME);
	}
	
	public static <T> Observable<T> mockSourceWithGetValue(Mockery mockery, final T expectedValue)
	{
		return mockObservableWithGetValue(mockery, SOURCE_NAME, expectedValue);
	}
	
	public static <T> Observable<T> mockSourceWithSetValue(Mockery mockery, final T expectedValue)
	{
		return mockObservableWithSetValue(mockery, SOURCE_NAME, expectedValue);
	}
	
	public static Observable<String> mockStringTarget(Mockery mockery)
	{
		return mockTarget(mockery);
	}
	
	public static <T> Observable<T> mockTarget(Mockery mockery)
	{
		return mockObservable(mockery, TARGET_NAME);
	}
	
	public static <T> Observable<T> mockTargetWithGetValue(Mockery mockery, final T expectedValue)
	{
		return mockObservableWithGetValue(mockery, TARGET_NAME, expectedValue);
	}
	
	public static <T> Observable<T> mockTargetWithSetValue(Mockery mockery, final T expectedValue)
	{
		return mockObservableWithSetValue(mockery, TARGET_NAME, expectedValue);
	}
	
	// private methods --------------------------------------------------------
	
	private static <T> Observable<T> mockObservable(Mockery mockery, String name)
	{
		// cannot safely mock generic types
		@SuppressWarnings("unchecked")
		Observable<T> observable = mockery.mock(Observable.class, name);
		
		return observable;
	}
	
	private static <T> Observable<T> mockObservableWithGetValue(Mockery mockery, String name, final T value)
	{
		final Observable<T> observable = mockObservable(mockery, name);
		
		mockery.checking(new Expectations() { {
			oneOf(observable).getValue(); will(returnValue(value));
		} });
		
		return observable;
	}
	
	private static <T> Observable<T> mockObservableWithSetValue(Mockery mockery, String name, final T expectedValue)
	{
		final Observable<T> observable = mockObservable(mockery, name);
		
		mockery.checking(new Expectations() { {
			oneOf(observable).setValue(expectedValue);
		} });
		
		return observable;
	}
}
