/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/DefaultBinderTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

/**
 * Tests {@code DefaultBinder}.
 * 
 * @author Mark Hobson
 * @version $Id: DefaultBinderTest.java 97099 2011-12-21 13:16:43Z mark@IIZUKA.CO.UK $
 * @see DefaultBinder
 */
public class DefaultBinderTest extends BinderTest
{
	// BinderTest methods -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <V> Binder<V> createBinder()
	{
		return new DefaultBinder<V>();
	}
}
