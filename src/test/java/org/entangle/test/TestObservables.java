/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/TestObservables.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
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
 * @version $Id: TestObservables.java 100485 2012-04-16 08:28:19Z mark@IIZUKA.CO.UK $
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
