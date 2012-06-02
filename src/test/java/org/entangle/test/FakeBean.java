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
package org.entangle.test;

/**
 * 
 * 
 * @author Mark Hobson
 */
public class FakeBean
{
	// fields -----------------------------------------------------------------
	
	private String name;
	
	private int age;

	private Object object;

	// constructors -----------------------------------------------------------
	
	public FakeBean()
	{
		// for JavaBean
	}
	
	// public methods ---------------------------------------------------------
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getReadOnlyName()
	{
		return "name";
	}
	
	public void setWriteOnlyName(@SuppressWarnings("unused") String name)
	{
		// write-only
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public Object getObject()
	{
		return object;
	}
	
	public void setObject(Object object)
	{
		this.object = object;
	}
}
