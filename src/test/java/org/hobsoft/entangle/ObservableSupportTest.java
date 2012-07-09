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
import static org.hobsoft.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;

import org.hobsoft.entangle.Observable;
import org.hobsoft.entangle.ObservableSupport;
import org.hobsoft.entangle.Observables;
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
