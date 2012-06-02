/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/ObservableSupport.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static uk.co.iizuka.common.binding.Utilities.checkNotNull;
import static uk.co.iizuka.common.binding.Utilities.safeEquals;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ObservableSupport.java 97740 2012-01-13 15:52:19Z mark@IIZUKA.CO.UK $
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
