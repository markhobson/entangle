/*
 * Copyright 2012 IIZUKA Software Technologies Ltd
 * 
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

import static org.entangle.Utilities.checkNotNull;

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
