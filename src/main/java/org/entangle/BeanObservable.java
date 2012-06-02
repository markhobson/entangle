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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListenerProxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Mark Hobson
 * @param <T>
 *            the type being observed
 */
class BeanObservable<T> implements Observable<T>
{
	// fields -----------------------------------------------------------------
	
	private final T bean;
	
	private final boolean bound;
	
	private final Map<ObservableListener<T>, PropertyChangeListener> adaptersByListener;
	
	// types ------------------------------------------------------------------
	
	private class ObservableListenerAdapter extends EventListenerProxy implements PropertyChangeListener
	{
		// constructors -----------------------------------------------------------
		
		public ObservableListenerAdapter(ObservableListener<T> listener)
		{
			super(listener);
		}
		
		// PropertyChangeListener methods -----------------------------------------
		
		/**
		 * {@inheritDoc}
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			Observable<T> source = BeanObservable.this;
			
			// TODO: supply old value?
			getListener().valueChanged(new ObservableEvent<T>(source, null, bean));
		}
		
		// EventListenerProxy methods -----------------------------------------
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public ObservableListener<T> getListener()
		{
			return (ObservableListener<T>) super.getListener();
		}
	}

	// constructors -----------------------------------------------------------

	public BeanObservable(T bean)
	{
		checkNotNull(bean, "bean cannot be null");

		this.bean = bean;
		
		bound = BeanUtils.isBound(bean);
		adaptersByListener = new HashMap<ObservableListener<T>, PropertyChangeListener>();
	}
	
	// Observable methods -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	public void addObservableListener(ObservableListener<T> listener)
	{
		checkBeanBound();
		checkNotNull(listener, "listener cannot be null");
		
		PropertyChangeListener adapter = new ObservableListenerAdapter(listener);
		
		// remember adapter so we can remove it in removeObservableListener
		adaptersByListener.put(listener, adapter);
		
		BeanUtils.addPropertyChangeListener(bean, adapter);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void removeObservableListener(ObservableListener<T> listener)
	{
		checkBeanBound();
		checkNotNull(listener, "listener cannot be null");
		
		if (adaptersByListener.containsKey(listener))
		{
			PropertyChangeListener adapter = adaptersByListener.remove(listener);
		
			BeanUtils.removePropertyChangeListener(bean, adapter);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T getValue()
	{
		return bean;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setValue(T value)
	{
		throw new UnsupportedOperationException("Bean is read-only");
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[bean=" + bean + "]";
	}
	
	// private methods --------------------------------------------------------
	
	private void checkBeanBound()
	{
		if (!bound)
		{
			throw new UnsupportedOperationException(String.format("Cannot observe unbound bean: %s", bean));
		}
	}
}
