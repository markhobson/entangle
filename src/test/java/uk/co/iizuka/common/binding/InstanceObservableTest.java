/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/InstanceObservableTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static uk.co.iizuka.common.binding.ObservableAssert.assertInstanceObservable;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListener;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListenerWithValueChanged;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code InstanceObservable}.
 * 
 * @author Mark Hobson
 * @version $Id: InstanceObservableTest.java 97741 2012-01-13 16:01:26Z mark@IIZUKA.CO.UK $
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
		
		assertEquals("uk.co.iizuka.common.binding.InstanceObservable[value=a]", observable.toString());
	}
}
