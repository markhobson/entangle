/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/ObservablesTest.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.ObservableAssert.assertDecoratedBeanObservable;
import static org.entangle.ObservableAssert.assertInstanceObservable;
import static org.entangle.ObservableAssert.assertNullObservable;

import org.entangle.Observables;
import org.entangle.test.FakeBean;
import org.junit.Test;


/**
 * Tests {@code Observables}.
 * 
 * @author Mark Hobson
 * @version $Id: ObservablesTest.java 97776 2012-01-16 11:32:07Z mark@IIZUKA.CO.UK $
 * @see Observables
 */
public class ObservablesTest
{
	// tests ------------------------------------------------------------------
	
	@Test
	public void nullObservable()
	{
		assertNullObservable(Observables.nullObservable());
	}
	
	@Test
	public void instance()
	{
		assertInstanceObservable(null, Observables.instance());
	}
	
	@Test
	public void instanceWithInstance()
	{
		Object instance = new Object();
		
		assertInstanceObservable(instance, Observables.instance(instance));
	}
	
	@Test
	public void bean()
	{
		FakeBean bean = new FakeBean();
		
		assertDecoratedBeanObservable(bean, Observables.bean(bean));
	}
}
