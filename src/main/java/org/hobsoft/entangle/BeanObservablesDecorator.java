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

import static org.hobsoft.entangle.Observables.compose;

import org.hobsoft.entangle.Observables.BeanObservables;
import org.hobsoft.entangle.Observables.ObservableFactory;

/**
 * Decorates an observable with the {@code BeanObservables} builder methods.
 * 
 * @author Mark Hobson
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
	public final BeanObservables<Object> property(String propertyName)
	{
		return property(propertyName, Object.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <U> BeanObservables<U> property(String propertyName, Class<U> propertyType)
	{
		BeanObservablesDecorator<U> property = new BeanObservablesDecorator<U>();
		
		property.setDelegate(compose(this, propertyFactoryT(property, propertyName, propertyType)));
		
		return property;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
			public Observable<U> createObservable(T bean)
			{
				return (bean != null) ? new PropertyObservable<U>(source, bean, propertyName, propertyType) : null;
			}
		};
	}
}
