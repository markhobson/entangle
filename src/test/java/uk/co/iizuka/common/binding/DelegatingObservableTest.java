/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/DelegatingObservableTest.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Tests {@code DelegatingObservable}.
 * 
 * @author Mark Hobson
 * @version $Id: DelegatingObservableTest.java 97962 2012-01-24 12:44:13Z mark@IIZUKA.CO.UK $
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
