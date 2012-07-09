/*
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
package org.hobsoft.entangle.test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 
 * 
 * @author Mark Hobson
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
