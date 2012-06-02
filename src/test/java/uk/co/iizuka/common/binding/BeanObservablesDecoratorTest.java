/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/BeanObservablesDecoratorTest.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListener;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListenerWithValueChanged;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.iizuka.common.binding.test.FakeBoundBean;

/**
 * Tests {@code BeanObservablesDecorator}.
 * 
 * @author Mark Hobson
 * @version $Id: BeanObservablesDecoratorTest.java 97959 2012-01-24 12:42:29Z mark@IIZUKA.CO.UK $
 * @see BeanObservablesDecorator
 */
@RunWith(JMock.class)
public class BeanObservablesDecoratorTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();
	
	private FakeBoundBean boundBean;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public void setUp()
	{
		boundBean = new FakeBoundBean();
	}

	// tests ------------------------------------------------------------------
	
	@Test
	public void propertyWithTypeGetValue()
	{
		boundBean.setChild(createBoundBean("x"));
		
		Observable<String> childName = childName(boundBean);
		
		assertEquals("x", childName.getValue());
	}
	
	@Test
	public void propertyWithTypeGetValueWhenBeanNull()
	{
		Observable<String> childName = childName(boundBean);

		// TODO: should we expect an exception to be thrown here rather than be lenient?
		assertNull(childName.getValue());
	}
	
	@Test
	public void propertyWithTypeGetValueTracksBean()
	{
		boundBean.setChild(new FakeBoundBean());
		
		Observable<String> childName = childName(boundBean);
		
		boundBean.setChild(createBoundBean("x"));
		
		assertEquals("x", childName.getValue());
	}
	
	@Test
	public void propertyWithTypeGetValueTracksBeanWhenNull()
	{
		Observable<String> childName = childName(boundBean);
		
		boundBean.setChild(createBoundBean("x"));
		
		assertEquals("x", childName.getValue());
	}
	
	@Test
	public void propertyWithTypeSetValue()
	{
		FakeBoundBean child = new FakeBoundBean();
		boundBean.setChild(child);
		
		Observable<String> childName = childName(boundBean);
		
		childName.setValue("x");
		
		assertEquals("x", child.getName());
	}
	
	@Test
	public void propertyWithTypeSetValueWhenBeanNull()
	{
		Observable<String> childName = childName(boundBean);
		
		// TODO: should we expect an exception to be thrown here rather than be lenient?
		childName.setValue("x");
		
		assertNull(boundBean.getChild());
	}
	
	@Test
	public void propertyWithTypeSetValueTracksBean()
	{
		boundBean.setChild(new FakeBoundBean());
		
		Observable<String> childName = childName(boundBean);
		
		FakeBoundBean child = new FakeBoundBean();
		boundBean.setChild(child);
		childName.setValue("x");
		
		assertEquals("x", child.getName());
	}
	
	@Test
	public void propertyWithTypeSetValueTracksBeanWhenNull()
	{
		Observable<String> childName = childName(boundBean);
		
		FakeBoundBean child = new FakeBoundBean();
		boundBean.setChild(child);
		childName.setValue("x");
		
		assertEquals("x", child.getName());
	}
	
	@Test
	public void propertyWithTypeAddObservableListener()
	{
		FakeBoundBean child = new FakeBoundBean();
		boundBean.setChild(child);
		
		Observable<String> childName = childName(boundBean);
		
		childName.addObservableListener(mockObservableListenerWithValueChanged(mockery, childName, null, "x"));
		
		child.setName("x");
	}
	
	@Test
	public void propertyWithTypeAddObservableListenerDoesNotListenToOldValue()
	{
		FakeBoundBean oldChild = new FakeBoundBean();
		boundBean.setChild(oldChild);
		
		Observable<String> childName = childName(boundBean);
		
		childName.addObservableListener(mockObservableListener(mockery, String.class));
		
		boundBean.setChild(new FakeBoundBean());
		oldChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeAddObservableListenerDoesNotListenToOldValueWhenNewValueNull()
	{
		FakeBoundBean oldChild = new FakeBoundBean();
		boundBean.setChild(oldChild);
		
		Observable<String> childName = childName(boundBean);
		
		childName.addObservableListener(mockObservableListener(mockery, String.class));
		
		boundBean.setChild(null);
		oldChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeAddObservableListenerListensToNewValue()
	{
		boundBean.setChild(new FakeBoundBean());
		
		Observable<String> childName = childName(boundBean);
		
		childName.addObservableListener(mockObservableListenerWithValueChanged(mockery, childName, null, "x"));
		
		FakeBoundBean newChild = new FakeBoundBean();
		boundBean.setChild(newChild);
		newChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeAddObservableListenerListensToNewValueWhenOldValueNull()
	{
		Observable<String> childName = childName(boundBean);
		
		childName.addObservableListener(mockObservableListenerWithValueChanged(mockery, childName, null, "x"));
		
		FakeBoundBean newChild = new FakeBoundBean();
		boundBean.setChild(newChild);
		newChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeRemoveObservableListener()
	{
		FakeBoundBean child = new FakeBoundBean();
		boundBean.setChild(child);
		
		Observable<String> childName = childName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		childName.addObservableListener(listener);
		childName.removeObservableListener(listener);
		
		child.setName("x");
	}
	
	@Test
	public void propertyWithTypeRemoveObservableListenerWhenOldValueNull()
	{
		Observable<String> childName = childName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		childName.addObservableListener(listener);
		childName.removeObservableListener(listener);
		
		FakeBoundBean newChild = new FakeBoundBean();
		boundBean.setChild(newChild);
		newChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeRemoveObservableListenerWhenNewValueNull()
	{
		FakeBoundBean oldChild = new FakeBoundBean();
		boundBean.setChild(oldChild);
		
		Observable<String> childName = childName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		childName.addObservableListener(listener);
		childName.removeObservableListener(listener);
		
		boundBean.setChild(null);
		oldChild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedGetValue()
	{
		boundBean.setChild(createBoundBean(createBoundBean("x")));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		assertEquals("x", grandchildName.getValue());
	}
	
	@Test
	public void propertyWithTypeNestedGetValueWhenBeanNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);
		
		// TODO: should we expect an exception to be thrown here rather than be lenient?
		assertNull(grandchildName.getValue());
	}
	
	@Test
	public void propertyWithTypeNestedGetValueTracksBean()
	{
		boundBean.setChild(createBoundBean(new FakeBoundBean()));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		boundBean.setChild(createBoundBean(createBoundBean("x")));
		
		assertEquals("x", grandchildName.getValue());
	}
	
	@Test
	public void propertyWithTypeNestedGetValueTracksBeanWhenNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);
		
		boundBean.setChild(createBoundBean(createBoundBean("x")));
		
		assertEquals("x", grandchildName.getValue());
	}
	
	@Test
	public void propertyWithTypeNestedSetValue()
	{
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		grandchildName.setValue("x");
		
		assertEquals("x", grandchild.getName());
	}
	
	@Test
	public void propertyWithTypeNestedSetValueWhenBeanNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);
		
		// TODO: should we expect an exception to be thrown here rather than be lenient?
		grandchildName.setValue("x");
		
		assertNull(boundBean.getChild());
	}
	
	@Test
	public void propertyWithTypeNestedSetValueTracksBean()
	{
		boundBean.setChild(createBoundBean(new FakeBoundBean()));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		grandchildName.setValue("x");
		
		assertEquals("x", grandchild.getName());
	}
	
	@Test
	public void propertyWithTypeNestedSetValueTracksBeanWhenNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);
		
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		grandchildName.setValue("x");
		
		assertEquals("x", grandchild.getName());
	}
	
	@Test
	public void propertyWithTypeNestedAddObservableListener()
	{
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);

		grandchildName.addObservableListener(mockObservableListenerWithValueChanged(mockery, grandchildName, null,
			"x"));
		
		grandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedAddObservableListenerDoesNotListenToOldValue()
	{
		FakeBoundBean oldGrandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(oldGrandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		grandchildName.addObservableListener(mockObservableListener(mockery, String.class));
		
		boundBean.setChild(new FakeBoundBean());
		oldGrandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedAddObservableListenerDoesNotListenToOldValueWhenNewValueNull()
	{
		FakeBoundBean oldGrandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(oldGrandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		grandchildName.addObservableListener(mockObservableListener(mockery, String.class));
		
		boundBean.setChild(null);
		oldGrandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedAddObservableListenerListensToNewValue()
	{
		boundBean.setChild(createBoundBean(new FakeBoundBean()));
		
		Observable<String> grandchildName = grandchildName(boundBean);

		grandchildName.addObservableListener(mockObservableListenerWithValueChanged(mockery, grandchildName, null,
			"x"));
		
		FakeBoundBean newGrandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(newGrandchild));
		newGrandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedAddObservableListenerListensToNewValueWhenOldValueNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);

		grandchildName.addObservableListener(mockObservableListenerWithValueChanged(mockery, grandchildName, null,
			"x"));
		
		FakeBoundBean newGrandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(newGrandchild));
		newGrandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedRemoveObservableListener()
	{
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		grandchildName.addObservableListener(listener);
		grandchildName.removeObservableListener(listener);
		
		grandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedRemoveObservableListenerWhenOldValueNull()
	{
		Observable<String> grandchildName = grandchildName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		grandchildName.addObservableListener(listener);
		grandchildName.removeObservableListener(listener);
		
		FakeBoundBean grandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(grandchild));
		grandchild.setName("x");
	}
	
	@Test
	public void propertyWithTypeNestedRemoveObservableListenerWhenNewValueNull()
	{
		FakeBoundBean oldGrandchild = new FakeBoundBean();
		boundBean.setChild(createBoundBean(oldGrandchild));
		
		Observable<String> grandchildName = grandchildName(boundBean);
		
		ObservableListener<String> listener = mockObservableListener(mockery);
		grandchildName.addObservableListener(listener);
		grandchildName.removeObservableListener(listener);
		
		boundBean.setChild(null);
		oldGrandchild.setName("x");
	}
	
	@Test
	public void toStringTest()
	{
		Observable<Object> delegate = Observables.instance();
		Observable<Object> decorated = BeanObservablesDecorator.decorate(delegate);
		
		assertEquals(delegate.toString(), decorated.toString());
	}
	
	// private methods --------------------------------------------------------
	
	private static Observable<String> childName(Object bean)
	{
		return BeanObservablesDecorator.decorate(property(bean, "child", FakeBoundBean.class))
			.property("name", String.class);
	}
	
	private static Observable<String> grandchildName(Object bean)
	{
		return BeanObservablesDecorator.decorate(property(bean, "child", FakeBoundBean.class))
			.property("child", FakeBoundBean.class)
			.property("name", String.class);
	}
	
	private static <T> PropertyObservable<T> property(Object bean, String propertyName, Class<T> propertyType)
	{
		return new PropertyObservable<T>(bean, propertyName, propertyType);
	}
	
	private static FakeBoundBean createBoundBean(String name)
	{
		FakeBoundBean bean = new FakeBoundBean();
		bean.setName(name);
		return bean;
	}
	
	private static FakeBoundBean createBoundBean(FakeBoundBean child)
	{
		FakeBoundBean bean = new FakeBoundBean();
		bean.setChild(child);
		return bean;
	}
}
