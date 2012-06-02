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
import static org.entangle.Utilities.safeEquals;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * 
 * 
 * @author Mark Hobson
 * @param <T>
 *            the type being observed
 */
final class ObservableSupport<T>
{
	// fields -----------------------------------------------------------------

	private final Observable<T> source;

	private final EventListenerList listeners;

	// constructors -----------------------------------------------------------

	public ObservableSupport(Observable<T> source)
	{
		this.source = source;

		listeners = new EventListenerList();
	}

	// public methods ---------------------------------------------------------

	public void addObservableListener(ObservableListener<T> listener)
	{
		checkNotNull(listener, "listener cannot be null");

		listeners.add(ObservableListener.class, listener);
	}

	public void removeObservableListener(ObservableListener<T> listener)
	{
		checkNotNull(listener, "listener cannot be null");

		listeners.remove(ObservableListener.class, listener);
	}

	public ObservableListener<T>[] getObservableListeners()
	{
		return listeners.getListeners(ObservableListener.class);
	}

	public void fireValueChanged(T oldValue, T newValue)
	{
		// don't fire event when values are equal
		if (safeEquals(oldValue, newValue))
		{
			return;
		}
		
		ObservableEvent<T> event = null;
		Object[] listenerList = listeners.getListenerList();

		for (int i = 0; i < listenerList.length; i += 2)
		{
			Class<?> listenerType = (Class<?>) listenerList[i];
			EventListener listener = (EventListener) listenerList[i + 1];
			
			if (ObservableListener.class.equals(listenerType))
			{
				if (event == null)
				{
					event = new ObservableEvent<T>(source, oldValue, newValue);
				}

				((ObservableListener<T>) listener).valueChanged(event);
			}
		}
	}
}
