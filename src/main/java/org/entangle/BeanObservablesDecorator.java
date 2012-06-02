/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/BeanObservablesDecorator.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.Observables.compose;

import org.entangle.Observables.BeanObservables;
import org.entangle.Observables.ObservableFactory;


/**
 * Decorates an observable with the {@code BeanObservables} builder methods.
 * 
 * @author Mark Hobson
 * @version $Id: BeanObservablesDecorator.java 97957 2012-01-24 12:31:57Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type being observed
 */
class BeanObservablesDecorator<T> extends DelegatingObservable<T> implements BeanObservables<T>
{
	// constructors -----------------------------------------------------------
	
	public BeanObservablesDecorator()
	{
		super();
	}
	
	public BeanObservablesDecorator(Observable<T> observable)
	{
		super(observable);
	}
	
	// BeanObservables methods ------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final BeanObservables<Object> property(String propertyName)
	{
		return property(propertyName, Object.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <U> BeanObservables<U> property(String propertyName, Class<U> propertyType)
	{
		BeanObservablesDecorator<U> property = new BeanObservablesDecorator<U>();
		
		property.setDelegate(compose(this, propertyFactoryT(property, propertyName, propertyType)));
		
		return property;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Observable<String> string(String propertyName)
	{
		return property(propertyName, String.class);
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> BeanObservablesDecorator<T> decorate(Observable<T> observable)
	{
		return new BeanObservablesDecorator<T>(observable);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getDelegate().toString();
	}
	
	// private methods --------------------------------------------------------
	
	private <U> ObservableFactory<T, U> propertyFactoryT(Observable<U> source, String propertyName,
		Class<U> propertyType)
	{
		return propertyFactory(source, propertyName, propertyType);
	}
	
	private static <T, U> ObservableFactory<T, U> propertyFactory(final Observable<U> source,
		final String propertyName, final Class<U> propertyType)
	{
		return new ObservableFactory<T, U>()
		{
			@Override
			public Observable<U> createObservable(T bean)
			{
				return (bean != null) ? new PropertyObservable<U>(source, bean, propertyName, propertyType) : null;
			}
		};
	}
}
