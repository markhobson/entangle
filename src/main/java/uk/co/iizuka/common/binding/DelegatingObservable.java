/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DelegatingObservable.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static uk.co.iizuka.common.binding.Utilities.checkNotNull;

/**
 * {@code Observable} implementation that delegates to another.
 * 
 * @author Mark Hobson
 * @version $Id: DelegatingObservable.java 97740 2012-01-13 15:52:19Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type being observed
 */
abstract class DelegatingObservable<T> implements Observable<T>
{
	// fields -----------------------------------------------------------------
	
	private Observable<T> delegate;
	
	// constructors -----------------------------------------------------------
	
	public DelegatingObservable()
	{
		this(Observables.<T>nullObservable());
	}
	
	public DelegatingObservable(Observable<T> delegate)
	{
		this.delegate = checkNotNull(delegate, "delegate cannot be null");
	}
	
	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObservableListener(ObservableListener<T> listener)
	{
		delegate.addObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObservableListener(ObservableListener<T> listener)
	{
		delegate.removeObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue()
	{
		return delegate.getValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value)
	{
		delegate.setValue(value);
	}
	
	// public methods ---------------------------------------------------------
	
	public Observable<T> getDelegate()
	{
		return delegate;
	}
	
	public void setDelegate(Observable<T> delegate)
	{
		this.delegate = checkNotNull(delegate, "delegate cannot be null");
	}
}
