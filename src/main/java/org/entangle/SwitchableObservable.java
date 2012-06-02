/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/SwitchableObservable.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * {@code Observable} decorator that maintains registered listeners when delegate changed.
 * 
 * @author Mark Hobson
 * @version $Id: SwitchableObservable.java 97740 2012-01-13 15:52:19Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type being observed
 */
class SwitchableObservable<T> extends DelegatingObservable<T>
{
	// fields -----------------------------------------------------------------
	
	private final ObservableSupport<T> support;
	
	// constructors -----------------------------------------------------------
	
	public SwitchableObservable()
	{
		this(Observables.<T>nullObservable());
	}
	
	public SwitchableObservable(Observable<T> delegate)
	{
		super(delegate);
		
		support = new ObservableSupport<T>(this);
	}
	
	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObservableListener(ObservableListener<T> listener)
	{
		support.addObservableListener(listener);
		
		super.addObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObservableListener(ObservableListener<T> listener)
	{
		support.removeObservableListener(listener);

		super.removeObservableListener(listener);
	}
	
	// DelegatingObservable methods -------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDelegate(Observable<T> delegate)
	{
		Observable<T> oldDelegate = getDelegate();
		super.setDelegate(delegate);
		delegateChanged(oldDelegate, getDelegate());
	}
	
	// private methods --------------------------------------------------------
	
	private void delegateChanged(Observable<T> oldDelegate, Observable<T> newDelegate)
	{
		removeObservableListeners(oldDelegate);
		
		addObservableListeners(newDelegate);

		support.fireValueChanged(oldDelegate.getValue(), newDelegate.getValue());
	}
	
	private void addObservableListeners(Observable<T> observable)
	{
		for (ObservableListener<T> listener : support.getObservableListeners())
		{
			observable.addObservableListener(listener);
		}
	}

	private void removeObservableListeners(Observable<T> observable)
	{
		for (ObservableListener<T> listener : support.getObservableListeners())
		{
			observable.removeObservableListener(listener);
		}
	}
}
