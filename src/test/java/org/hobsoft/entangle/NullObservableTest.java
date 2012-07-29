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

import static org.hobsoft.entangle.test.TestObservableListeners.mockObservableListener;
import static org.junit.Assert.assertNull;

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
