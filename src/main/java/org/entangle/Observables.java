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
package org.entangle;

/**
 * Factory for creating observables.
 * 
 * @author Mark Hobson
 * @see Observable
 */
public final class Observables
{
	// types ------------------------------------------------------------------
	
	/**
	 * JavaBean Observable value and property value builder.
	 * 
	 * @param <T>
	 *            the type being observed
	 */
	public interface BeanObservables<T> extends Observable<T>
	{
		BeanObservables<Object> property(String propertyName);
		
		<U> BeanObservables<U> property(String propertyName, Class<U> propertyType);
		
		Observable<String> string(String propertyName);
		
		// TODO: other basic property type convenience methods -- how to name?
	}
	
	/**
	 * Factory that creates observables from an object.
	 * 
	 * @param <T>
	 *            the type of object this factory creates observables from
	 * @param <U>
	 *            the type of observable this factory creates
	 */
	public interface ObservableFactory<T, U>
	{
		Observable<U> createObservable(T value);
	}
	
	// constructors -----------------------------------------------------------
	
	private Observables()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <T> Observable<T> nullObservable()
	{
		return NullObservable.INSTANCE.asSubtype();
	}
	
	public static <T> Observable<T> instance()
	{
		return instance(null);
	}
	
	public static <T> Observable<T> instance(T instance)
	{
		return new InstanceObservable<T>(instance);
	}
	
	public static <T> BeanObservables<T> bean(T bean)
	{
		return BeanObservablesDecorator.decorate(new BeanObservable<T>(bean));
	}
	
	public static <T, U> Observable<U> compose(Observable<T> parent, ObservableFactory<T, U> childFactory)
	{
		return new HierarchicalCompositeObservable<T, U>(parent, childFactory);
	}
}
