/*
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
package org.hobsoft.entangle;

import org.hobsoft.entangle.test.FakeBean;
import org.junit.Test;

import static org.hobsoft.entangle.ObservableAssert.assertDecoratedBeanObservable;
import static org.hobsoft.entangle.ObservableAssert.assertInstanceObservable;
import static org.hobsoft.entangle.ObservableAssert.assertNullObservable;

/**
 * Tests {@code Observables}.
 * 
 * @author Mark Hobson
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
