/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/ObservableEvent.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static uk.co.iizuka.common.binding.Utilities.safeEquals;
import static uk.co.iizuka.common.binding.Utilities.safeHashCode;

import java.util.EventObject;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ObservableEvent.java 97725 2012-01-11 15:45:41Z mark@IIZUKA.CO.UK $
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
