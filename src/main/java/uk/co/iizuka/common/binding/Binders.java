/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Binders.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

/**
 * Factory for creating binders.
 * 
 * @author Mark Hobson
 * @version $Id: Binders.java 97099 2011-12-21 13:16:43Z mark@IIZUKA.CO.UK $
 * @see Binder
 */
public final class Binders
{
	// TODO: provide convenience factory method to create Binder<Void>?
	
	// constructors -----------------------------------------------------------
	
	private Binders()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	/**
	 * Creates a new {@code Binder}.
	 * 
	 * @param <V>
	 *            the type of violation that the binder can produce
	 * @return the binder
	 */
	public static <V> Binder<V> newBinder()
	{
		return new DefaultBinder<V>();
	}
}
