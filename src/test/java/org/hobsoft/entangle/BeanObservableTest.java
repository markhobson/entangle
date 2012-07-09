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

import static org.hobsoft.entangle.ObservableAssert.assertBeanObservable;
import static org.hobsoft.entangle.test.TestObservableListeners.mockObservableListener;
import static org.hobsoft.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.hobsoft.entangle.BeanObservable;
import org.hobsoft.entangle.ObservableListener;
import org.hobsoft.entangle.test.FakeBean;
import org.hobsoft.entangle.test.FakeBoundBean;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code BeanObservable}.
 * 
 * @author Mark Hobson
 * @see BeanObservable
 */
@RunWith(JMock.class)
public class BeanObservableTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();
	
	private FakeBean bean;
	
	private BeanObservable<FakeBean> observable;
	
	private FakeBoundBean boundBean;
	
	private BeanObservable<FakeBoundBean> boundObservable;
	
	// public methods ---------------------------------------------------------

	@Before
	public void setUp()
	{
		bean = new FakeBean();
		observable = new BeanObservable<FakeBean>(bean);
		
		boundBean = new FakeBoundBean();
		boundObservable = new BeanObservable<FakeBoundBean>(boundBean);
	}

	// tests ------------------------------------------------------------------

	@Test(expected = NullPointerException.class)
	public void newPropertyObservableWithNullBean()
	{
		new BeanObservable<FakeBean>(null);
	}
	
	@Test
	public void newPropertyObservableWithUnboundBean()
	{
		BeanObservable<FakeBean> observable = new BeanObservable<FakeBean>(bean);
		
		assertBeanObservable(bean, observable);
	}
	
	@Test
	public void newPropertyObservableWithBoundBean()
	{
		BeanObservable<FakeBoundBean> observable = new BeanObservable<FakeBoundBean>(boundBean);
		
		assertBeanObservable(boundBean, observable);
	}
	
	@Test
	public void getValue()
	{
		assertSame("bean", bean, observable.getValue());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void setValue()
	{
		observable.setValue(new FakeBean());
	}

	@Test
	public void addObservableListenerReceivesEventWhenPropertyChanged()
	{
		// TODO: expect old value?
		boundObservable.addObservableListener(mockObservableListenerWithValueChanged(mockery, boundObservable, null,
			boundBean));
		
		boundBean.setName("a");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void addObservableListenerWhenBeanUnbound()
	{
		observable.addObservableListener(mockObservableListener(mockery, FakeBean.class));
	}

	@Test(expected = NullPointerException.class)
	public void addObservableListenerWithNull()
	{
		boundObservable.addObservableListener(null);
	}

	@Test
	public void removeObservableListenerNoLongerReceivesEventWhenPropertyChanged()
	{
		ObservableListener<FakeBoundBean> listener = mockObservableListener(mockery);
		boundObservable.addObservableListener(listener);
		boundObservable.removeObservableListener(listener);
		
		boundBean.setName("a");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void removeObservableListenerWhenBeanUnbound()
	{
		observable.removeObservableListener(mockObservableListener(mockery, FakeBean.class));
	}
	
	@Test(expected = NullPointerException.class)
	public void removeObservableListenerWithNull()
	{
		boundObservable.removeObservableListener(null);
	}
	
	@Test
	public void toStringTest()
	{
		String expected = "org.hobsoft.entangle.BeanObservable[bean=" + bean + "]";
		assertEquals(expected, observable.toString());
	}
}
