/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/NullObservable.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
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
