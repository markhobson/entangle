/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/FakeBoundBean.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding.test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: FakeBoundBean.java 97740 2012-01-13 15:52:19Z mark@IIZUKA.CO.UK $
 */
public class FakeBoundBean
{
	// fields -----------------------------------------------------------------
	
	private final PropertyChangeSupport support;
	
	private String name;
	
	private String writeOnlyName;
	
	private int age;
	
	private FakeBoundBean child;
	
	private Object object;

	// constructors -----------------------------------------------------------
	
	public FakeBoundBean()
	{
		support = new PropertyChangeSupport(this);
	}
	
	// public methods ---------------------------------------------------------
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		support.removePropertyChangeListener(listener);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		String oldName = this.name;
		this.name = name;
		support.firePropertyChange("name", oldName, this.name);
	}
	
	public String getReadOnlyName()
	{
		return "name";
	}
	
	public void setWriteOnlyName(String name)
	{
		String oldWriteOnlyName = this.writeOnlyName;
		this.writeOnlyName = name;
		support.firePropertyChange("writeOnlyName", oldWriteOnlyName, this.writeOnlyName);
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		int oldAge = this.age;
		this.age = age;
		support.firePropertyChange("age", oldAge, this.age);
	}
	
	public FakeBoundBean getChild()
	{
		return child;
	}
	
	public void setChild(FakeBoundBean child)
	{
		FakeBoundBean oldChild = this.child;
		this.child = child;
		support.firePropertyChange("child", oldChild, this.child);
	}
	
	public Object getObject()
	{
		return object;
	}
	
	public void setObject(Object object)
	{
		Object oldObject = this.object;
		this.object = object;
		support.firePropertyChange("object", oldObject, this.object);
	}
}
