/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/BeanUtils.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.Utilities.checkArgument;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.FeatureDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EventListener;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: BeanUtils.java 97601 2012-01-05 15:47:57Z mark@IIZUKA.CO.UK $
 */
final class BeanUtils
{
	// constants --------------------------------------------------------------
	
	private static final String PROPERTY_CHANGE_EVENT = "propertyChange";

	// constructors -----------------------------------------------------------
	
	private BeanUtils()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName)
	{
		BeanInfo beanInfo = getBeanInfo(bean);
		
		PropertyDescriptor propertyDescriptor = getFeatureDescriptor(propertyName, beanInfo.getPropertyDescriptors());

		checkArgument(propertyDescriptor != null, "No such property '%s' on bean: %s", propertyName, bean);
		
		return propertyDescriptor;
	}
	
	public static Object getPropertyValue(Object bean, String propertyName)
	{
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean, propertyName);
		
		Method method = propertyDescriptor.getReadMethod();
		
		checkArgument(method != null, "Cannot read write-only property '%s' on bean: %s", propertyName, bean);
		
		try
		{
			return method.invoke(bean);
		}
		catch (IllegalAccessException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error reading property '%s' on bean: %s", propertyName, bean),
				exception);
		}
		catch (InvocationTargetException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error reading property '%s' on bean: %s", propertyName, bean),
				exception.getCause());
		}
	}
	
	public static void setPropertyValue(Object bean, String propertyName, Object propertyValue)
	{
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean, propertyName);
		
		Method method = propertyDescriptor.getWriteMethod();
		
		checkArgument(method != null, "Cannot write read-only property '%s' on bean: %s", propertyName, bean);
		
		boolean primitive = propertyDescriptor.getPropertyType().isPrimitive();
		checkArgument(!primitive || propertyValue != null, "Cannot write null to primitive property '%s' on bean: %s",
			propertyName, bean);
		
		try
		{
			method.invoke(bean, propertyValue);
		}
		catch (IllegalAccessException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error writing property '%s' on bean: %s", propertyName, bean),
				exception);
		}
		catch (InvocationTargetException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error writing property '%s' on bean: %s", propertyName, bean),
				exception.getCause());
		}
	}
	
	public static boolean isBound(Object bean)
	{
		try
		{
			getPropertyChangeDescriptor(bean);
			
			return true;
		}
		catch (IllegalArgumentException exception)
		{
			return false;
		}
	}
	
	public static void addPropertyChangeListener(Object bean, PropertyChangeListener listener)
	{
		addEventSetListener(bean, PROPERTY_CHANGE_EVENT, listener);
	}
	
	public static void addPropertyChangeListener(Object bean, String propertyName, PropertyChangeListener listener)
	{
		addPropertyChangeListener(bean, new PropertyChangeListenerProxy(propertyName, listener));
	}
	
	public static void removePropertyChangeListener(Object bean, PropertyChangeListener listener)
	{
		removeEventSetListener(bean, PROPERTY_CHANGE_EVENT, listener);
	}
	
	public static void removePropertyChangeListener(Object bean, String propertyName, PropertyChangeListener listener)
	{
		removePropertyChangeListener(bean, new PropertyChangeListenerProxy(propertyName, listener));
	}
	
	// private methods --------------------------------------------------------
	
	private static BeanInfo getBeanInfo(Object bean)
	{
		try
		{
			return Introspector.getBeanInfo(bean.getClass());
		}
		catch (IntrospectionException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException("Error introspecting bean: " + bean, exception);
		}
	}

	private static <T extends FeatureDescriptor> T getFeatureDescriptor(String name, T[] featureDescriptors)
	{
		for (T featureDescriptor : featureDescriptors)
		{
			if (name.equals(featureDescriptor.getName()))
			{
				return featureDescriptor;
			}
		}
		
		return null;
	}
	
	private static void addEventSetListener(Object bean, String eventSetName, EventListener listener)
	{
		addEventSetListener(bean, getEventSetDescriptor(bean, eventSetName), listener);
	}
	
	private static void addEventSetListener(Object bean, EventSetDescriptor eventSetDescriptor, EventListener listener)
	{
		Method method = eventSetDescriptor.getAddListenerMethod();
		
		try
		{
			method.invoke(bean, listener);
		}
		catch (IllegalAccessException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error adding event set '%s' listener to bean: %s",
				eventSetDescriptor.getName(), bean), exception);
		}
		catch (InvocationTargetException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error adding event set '%s' listener to bean: %s",
				eventSetDescriptor.getName(), bean), exception);
		}
	}
	
	private static void removeEventSetListener(Object bean, String eventSetName, EventListener listener)
	{
		removeEventSetListener(bean, getEventSetDescriptor(bean, eventSetName), listener);
	}
	
	private static void removeEventSetListener(Object bean, EventSetDescriptor eventSetDescriptor,
		EventListener listener)
	{
		Method method = eventSetDescriptor.getRemoveListenerMethod();
		
		try
		{
			method.invoke(bean, listener);
		}
		catch (IllegalAccessException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error removing event set '%s' listener from bean: %s",
				eventSetDescriptor.getName(), bean), exception);
		}
		catch (InvocationTargetException exception)
		{
			// TODO: handle exception better?
			throw new RuntimeException(String.format("Error removing event set '%s' listener from bean: %s",
				eventSetDescriptor.getName(), bean), exception);
		}
	}
	
	private static EventSetDescriptor getEventSetDescriptor(Object bean, String eventSetName)
	{
		BeanInfo beanInfo = getBeanInfo(bean);
		
		EventSetDescriptor eventSetDescriptor = getFeatureDescriptor(eventSetName, beanInfo.getEventSetDescriptors());

		checkArgument(eventSetDescriptor != null, "No such event set '%s' on bean: %s", eventSetName, bean);
		
		return eventSetDescriptor;
	}
	
	private static EventSetDescriptor getPropertyChangeDescriptor(Object bean)
	{
		return getEventSetDescriptor(bean, PROPERTY_CHANGE_EVENT);
	}
}
