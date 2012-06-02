/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/TestObservableListeners.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import uk.co.iizuka.common.binding.Observable;
import uk.co.iizuka.common.binding.ObservableEvent;
import uk.co.iizuka.common.binding.ObservableListener;

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
