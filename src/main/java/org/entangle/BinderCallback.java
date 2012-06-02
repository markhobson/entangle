/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/BinderCallback.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: BinderCallback.java 97110 2011-12-21 13:44:26Z mark@IIZUKA.CO.UK $
 * @param <V> 
 *            the binding violation type that this callback accepts
 */
interface BinderCallback<V>
{
	void built(Binding<?, ?, V> binding);
}
