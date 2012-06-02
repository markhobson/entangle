/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/FakeBean.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle.test;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: FakeBean.java 97580 2012-01-05 11:42:20Z mark@IIZUKA.CO.UK $
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
