/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/ObservableEventTest.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@code ObservableEvent}.
 * 
 * @author Mark Hobson
 * @version $Id: ObservableEventTest.java 97725 2012-01-11 15:45:41Z mark@IIZUKA.CO.UK $
 * @see ObservableEvent
 */
public class ObservableEventTest
{
	// fields -----------------------------------------------------------------
	
	private Observable<String> source;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public void setUp()
	{
		source = Observables.nullObservable();
	}

	// tests ------------------------------------------------------------------
	
	@Test
	public void newObservableEvent()
	{
		ObservableEvent<String> actual = new ObservableEvent<String>(source, "x", "y");
		
		assertObservableEvent(source, "x", "y", actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void newObservableEventWithNullSource()
	{
		new ObservableEvent<String>(null, "x", "y");
	}
	
	@Test
	public void newObservableEventWithNullOldValue()
	{
		ObservableEvent<String> actual = new ObservableEvent<String>(source, null, "y");
		
		assertObservableEvent(source, null, "y", actual);
	}
	
	@Test
	public void newObservableEventWithNullNewValue()
	{
		ObservableEvent<String> actual = new ObservableEvent<String>(source, "x", null);
		
		assertObservableEvent(source, "x", null, actual);
	}
	
	@Test
	public void hashCodeEqualsWhenEquals()
	{
		ObservableEvent<String> event1 = new ObservableEvent<String>(source, "x", "y");
		ObservableEvent<String> event2 = new ObservableEvent<String>(source, "x", "y");
		
		assertEquals(event1.hashCode(), event2.hashCode());
	}
	
	@Test
	public void equalsWhenEquals()
	{
		ObservableEvent<String> event1 = new ObservableEvent<String>(source, "x", "y");
		ObservableEvent<String> event2 = new ObservableEvent<String>(source, "x", "y");
		
		assertTrue(event1.equals(event2));
	}
	
	@Test
	public void equalsWhenSourceDiffers()
	{
		Observable<String> source1 = Observables.instance();
		ObservableEvent<String> event1 = new ObservableEvent<String>(source1, "x", "y");
		
		Observable<String> source2 = Observables.instance();
		ObservableEvent<String> event2 = new ObservableEvent<String>(source2, "x", "y");
		
		assertFalse(event1.equals(event2));
	}
	
	@Test
	public void equalsWhenOldValueDiffers()
	{
		ObservableEvent<String> event1 = new ObservableEvent<String>(source, "x", "y");
		ObservableEvent<String> event2 = new ObservableEvent<String>(source, "z", "y");
		
		assertFalse(event1.equals(event2));
	}
	
	@Test
	public void equalsWhenNewValueDiffers()
	{
		ObservableEvent<String> event1 = new ObservableEvent<String>(source, "x", "y");
		ObservableEvent<String> event2 = new ObservableEvent<String>(source, "x", "z");
		
		assertFalse(event1.equals(event2));
	}
	
	@Test
	public void toStringTest()
	{
		ObservableEvent<String> event = new ObservableEvent<String>(source, "x", "y");

		String expected = "uk.co.iizuka.common.binding.ObservableEvent[source=" + source + ", oldValue=x, newValue=y]";
		assertEquals(expected, event.toString());
	}
	
	// private methods --------------------------------------------------------
	
	private static <T> void assertObservableEvent(Observable<T> expectedSource, T expectedOldValue, T expectedNewValue,
		ObservableEvent<T> actual)
	{
		assertEquals("source", expectedSource, actual.getSource());
		assertEquals("oldValue", expectedOldValue, actual.getOldValue());
		assertEquals("newValue", expectedNewValue, actual.getNewValue());
	}
}
