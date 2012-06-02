/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/InstanceObservable.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: InstanceObservable.java 96678 2011-12-15 09:50:22Z mark@IIZUKA.CO.UK $
 * @param <T> 
 *            the type being observed
 */
final class InstanceObservable<T> extends AbstractObservable<T>
{
	// fields -----------------------------------------------------------------
	
	private T value;

	// constructors -----------------------------------------------------------
	
	public InstanceObservable()
	{
		this(null);
	}
	
	public InstanceObservable(T value)
	{
		this.value = value;
	}
	
	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getValue()
	{
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(T value)
	{
		T oldValue = this.value;
		this.value = value;
		
		fireValueChanged(oldValue, this.value);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[value=" + value + "]";
	}
}
