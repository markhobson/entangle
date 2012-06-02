/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/PropertyObservable.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static uk.co.iizuka.common.binding.Utilities.checkArgument;
import static uk.co.iizuka.common.binding.Utilities.checkNotNull;
import static uk.co.iizuka.common.binding.Utilities.firstNonNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: PropertyObservable.java 98707 2012-02-23 12:47:17Z mark@IIZUKA.CO.UK $
 * @param <T> 
 *            the type being observed
 */
final class PropertyObservable<T> implements Observable<T>
{
	// types ------------------------------------------------------------------
	
	/**
	 * Workaround for Java7 bug 7148143.
	 * 
	 * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7148143">Java bug 7148143</a>
	 */
	private abstract static class EventListenerProxy2 implements EventListener
	{
		// fields -----------------------------------------------------------------
		
		private final EventListener listener;
		
		// constructors -----------------------------------------------------------
		
		public EventListenerProxy2(EventListener listener)
		{
			this.listener = listener;
		}
		
		// public methods ---------------------------------------------------------
		
		public EventListener getListener()
		{
			return listener;
		}
	}
	
	// TODO: extend java.util.EventListenerProxy when Java bug 7148143 fixed
	private class ObservableListenerAdapter extends EventListenerProxy2 implements PropertyChangeListener
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
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			T oldValue = getBoxedPropertyType().cast(event.getOldValue());
			T newValue = getBoxedPropertyType().cast(event.getNewValue());
			
			getListener().valueChanged(new ObservableEvent<T>(source, oldValue, newValue));
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

	// fields -----------------------------------------------------------------
	
	private final Observable<T> source;
	
	private final Object bean;
	
	private final String propertyName;
	
	private final Class<T> propertyType;
	
	private final boolean bound;
	
	private final Map<ObservableListener<T>, PropertyChangeListener> adaptersByListener;
	
	// constructors -----------------------------------------------------------
	
	public PropertyObservable(Object bean, String propertyName, Class<T> propertyType)
	{
		this(null, bean, propertyName, propertyType);
	}
	
	public PropertyObservable(Observable<T> source, Object bean, String propertyName, Class<T> propertyType)
	{
		checkNotNull(bean, "bean cannot be null");
		checkNotNull(propertyName, "propertyName cannot be null");
		checkNotNull(propertyType, "propertyType cannot be null");
		
		PropertyDescriptor property = BeanUtils.getPropertyDescriptor(bean, propertyName);
		
		checkArgument(propertyType.equals(property.getPropertyType()),
			"propertyType does not match bean property '%s' type '%s': %s", propertyName,
			property.getPropertyType().getName(), propertyType.getName());

		this.source = firstNonNull(source, this);
		this.bean = bean;
		this.propertyName = propertyName;
		this.propertyType = propertyType;

		bound = BeanUtils.isBound(bean);
		adaptersByListener = new HashMap<ObservableListener<T>, PropertyChangeListener>();
	}

	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObservableListener(ObservableListener<T> listener)
	{
		checkPropertyBound();
		checkNotNull(listener, "listener cannot be null");
		
		PropertyChangeListener adapter = new ObservableListenerAdapter(listener);
		
		// remember adapter so we can remove it in removeObservableListener
		adaptersByListener.put(listener, adapter);
		
		BeanUtils.addPropertyChangeListener(bean, propertyName, adapter);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObservableListener(ObservableListener<T> listener)
	{
		checkPropertyBound();
		checkNotNull(listener, "listener cannot be null");
		
		if (adaptersByListener.containsKey(listener))
		{
			PropertyChangeListener adapter = adaptersByListener.remove(listener);
		
			BeanUtils.removePropertyChangeListener(bean, propertyName, adapter);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue()
	{
		Object value = BeanUtils.getPropertyValue(bean, propertyName);
		
		return getBoxedPropertyType().cast(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value)
	{
		BeanUtils.setPropertyValue(bean, propertyName, value);
	}
	
	// public methods ---------------------------------------------------------
	
	public Object getBean()
	{
		return bean;
	}
	
	public String getPropertyName()
	{
		return propertyName;
	}
	
	public Class<T> getPropertyType()
	{
		return propertyType;
	}
	
	public static PropertyObservable<Object> get(Object bean, String propertyName)
	{
		return get(bean, propertyName, Object.class);
	}
	
	public static <T> PropertyObservable<T> get(Object bean, String propertyName, Class<T> propertyType)
	{
		return new PropertyObservable<T>(bean, propertyName, propertyType);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[bean=" + bean + ",propertyName=" + propertyName + "]";
	}
	
	// private methods --------------------------------------------------------
	
	private void checkPropertyBound()
	{
		if (!bound)
		{
			throw new UnsupportedOperationException(String.format("Cannot observe unbound property '%s' on bean: %s",
				propertyName, bean));
		}
	}
	
	private Class<T> getBoxedPropertyType()
	{
		return ClassUtils.box(propertyType);
	}
}
