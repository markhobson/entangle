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
