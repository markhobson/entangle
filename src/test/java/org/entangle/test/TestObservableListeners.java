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
package org.entangle.test;

import org.entangle.Observable;
import org.entangle.ObservableEvent;
import org.entangle.ObservableListener;
import org.jmock.Expectations;
import org.jmock.Mockery;


/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: TestObservableListeners.java 100485 2012-04-16 08:28:19Z mark@IIZUKA.CO.UK $
 */
public final class TestObservableListeners
{
	// constructors -----------------------------------------------------------
	
	private TestObservableListeners()
	{
		throw new AssertionError();
	}

	// public methods ---------------------------------------------------------
	
	public static <T> ObservableListener<T> mockObservableListener(Mockery mockery, Class<T> type)
	{
		return mockObservableListener(mockery);
	}
	
	public static <T> ObservableListener<T> mockObservableListener(Mockery mockery)
	{
		// cannot safely mock generic types
		@SuppressWarnings("unchecked")
		ObservableListener<T> listener = mockery.mock(ObservableListener.class);

		return listener;
	}
	
	public static <T> ObservableListener<T> mockObservableListenerWithValueChanged(Mockery mockery,
		Observable<T> expectedSource, T expectedOldValue, T expectedNewValue)
	{
		final ObservableListener<T> listener = mockObservableListener(mockery);
		
		final ObservableEvent<T> expectedEvent = new ObservableEvent<T>(expectedSource, expectedOldValue,
			expectedNewValue);
		
		mockery.checking(new Expectations() { {
			oneOf(listener).valueChanged(expectedEvent);
		} });
		
		return listener;
	}
}
