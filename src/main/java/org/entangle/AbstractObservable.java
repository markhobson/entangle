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

/**
 * 
 * 
 * @author Mark Hobson
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
