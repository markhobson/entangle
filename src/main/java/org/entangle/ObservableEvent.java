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

import static org.entangle.Utilities.safeEquals;
import static org.entangle.Utilities.safeHashCode;

import java.util.EventObject;

/**
 * 
 * 
 * @author Mark Hobson
 * @param <T> 
 *            the type being observed
 */
public final class ObservableEvent<T> extends EventObject
{
	// fields -----------------------------------------------------------------
	
	private final T oldValue;
	
	private final T newValue;

	// constructors -----------------------------------------------------------
	
	public ObservableEvent(Observable<T> source, T oldValue, T newValue)
	{
		super(source);
		
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	// EventObject methods ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<T> getSource()
	{
		return (Observable<T>) source;
	}
	
	// public methods ---------------------------------------------------------
	
	public T getOldValue()
	{
		return oldValue;
	}
	
	public T getNewValue()
	{
		return newValue;
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		int hashCode = source.hashCode();
		hashCode = (hashCode * 31) + safeHashCode(oldValue);
		hashCode = (hashCode * 31) + safeHashCode(newValue);
		
		return hashCode;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof ObservableEvent))
		{
			return false;
		}
		
		ObservableEvent<?> event = (ObservableEvent<?>) object;
		
		return source.equals(event.getSource())
			&& safeEquals(oldValue, event.getOldValue())
			&& safeEquals(newValue, event.getNewValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[source=" + source + ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}
}
