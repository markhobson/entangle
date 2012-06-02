/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Observables.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * Factory for creating observables.
 * 
 * @author Mark Hobson
 * @version $Id: Observables.java 97824 2012-01-17 10:25:46Z mark@IIZUKA.CO.UK $
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
