/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/NullObservableTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.test.TestObservableListeners.mockObservableListener;
import static org.junit.Assert.assertNull;

import org.entangle.NullObservable;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code NullObservable}.
 * 
 * @author Mark Hobson
 * @version $Id: NullObservableTest.java 97409 2011-12-30 17:27:16Z mark@IIZUKA.CO.UK $
 * @see NullObservable
 */
@RunWith(JMock.class)
public class NullObservableTest
{
	// fields -----------------------------------------------------------------
	
	private final Mockery mockery = new JUnit4Mockery();

	private NullObservable observable;
	
	// public methods ---------------------------------------------------------

	@Before
	public void setUp()
	{
		observable = NullObservable.INSTANCE;
	}
	
	// tests ------------------------------------------------------------------

	@Test
	public void getValue()
	{
		assertNull(observable.getValue());
	}
	
	@Test
	public void setValueIgnoresValue()
	{
		observable.setValue(new Object());
		
		assertNull(observable.getValue());
	}
	
	@Test
	public void setValueDoesNotFireEvent()
	{
		observable.addObservableListener(mockObservableListener(mockery));
		
		observable.setValue(new Object());
	}
}
