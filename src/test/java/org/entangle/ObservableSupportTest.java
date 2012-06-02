/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/ObservableSupportTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.test.TestObservableListeners.mockObservableListener;
import static org.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;

import org.entangle.Observable;
import org.entangle.ObservableSupport;
import org.entangle.Observables;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code ObservableSupport}.
 * 
 * @author Mark Hobson
 * @version $Id: ObservableSupportTest.java 97741 2012-01-13 16:01:26Z mark@IIZUKA.CO.UK $
 * @see ObservableSupport
 */
@RunWith(JMock.class)
public class ObservableSupportTest
{
	// fields -----------------------------------------------------------------
	
	private final Mockery mockery = new JUnit4Mockery();
	
	private Observable<String> source;
	
	private ObservableSupport<String> support;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public void setUp()
	{
		source = Observables.instance();
		support = new ObservableSupport<String>(source);
	}
	
	// tests ------------------------------------------------------------------
	
	@Test
	public void fireValueChangedFiresEvent()
	{
		support.addObservableListener(mockObservableListenerWithValueChanged(mockery, source, "a", "b"));
		
		support.fireValueChanged("a", "b");
	}
	
	@Test
	public void fireValueChangedDoesNotFireEventWhenValuesEqual()
	{
		support.addObservableListener(mockObservableListener(mockery, String.class));
		
		support.fireValueChanged("a", "a");
	}
}
