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
 * @version $Id: NullObservable.java 97409 2011-12-30 17:27:16Z mark@IIZUKA.CO.UK $
 */
enum NullObservable implements Observable<Object>
{
	// constants --------------------------------------------------------------
	
	INSTANCE
	{
		// Observable methods -----------------------------------------------------
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addObservableListener(ObservableListener<Object> listener)
		{
			// no-op
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeObservableListener(ObservableListener<Object> listener)
		{
			// no-op
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getValue()
		{
			return null;
		}
	
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setValue(Object value)
		{
			// no-op
		}
	};
	
	// public methods ---------------------------------------------------------

	public <T> Observable<T> asSubtype()
	{
		// safe for all types
		@SuppressWarnings("unchecked")
		Observable<T> observable = (Observable<T>) this;
		
		return observable;
	}
}
