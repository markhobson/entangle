/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/BeanObservableTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static uk.co.iizuka.common.binding.ObservableAssert.assertBeanObservable;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListener;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListenerWithValueChanged;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.iizuka.common.binding.test.FakeBean;
import uk.co.iizuka.common.binding.test.FakeBoundBean;

/**
 * Tests {@code BeanObservable}.
 * 
 * @author Mark Hobson
 * @version $Id: BeanObservableTest.java 97776 2012-01-16 11:32:07Z mark@IIZUKA.CO.UK $
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
		String expected = "uk.co.iizuka.common.binding.BeanObservable[bean=" + bean + "]";
		assertEquals(expected, observable.toString());
	}
}
