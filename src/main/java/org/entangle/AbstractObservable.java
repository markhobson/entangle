/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/AbstractObservable.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: AbstractObservable.java 96678 2011-12-15 09:50:22Z mark@IIZUKA.CO.UK $
 * @param <T> 
 *            the type being observed
 */
abstract class AbstractObservable<T> implements Observable<T>
{
	// fields -----------------------------------------------------------------
	
	private final ObservableSupport<T> support;
	
	// constructors -----------------------------------------------------------
	
	public AbstractObservable()
	{
		support = new ObservableSupport<T>(this);
	}

	// Observable methods -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void addObservableListener(ObservableListener<T> listener)
	{
		support.addObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void removeObservableListener(ObservableListener<T> listener)
	{
		support.removeObservableListener(listener);
	}
	
	// protected methods ------------------------------------------------------
	
	protected final void fireValueChanged(T oldValue, T newValue)
	{
		support.fireValueChanged(oldValue, newValue);
	}
}
