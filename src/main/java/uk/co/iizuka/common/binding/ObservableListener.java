/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/ObservableListener.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import java.util.EventListener;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: ObservableListener.java 88825 2011-06-08 21:18:39Z mark@IIZUKA.CO.UK $
 * @param <T> 
 *            the type being observed
 */
public interface ObservableListener<T> extends EventListener
{
	void valueChanged(ObservableEvent<T> event);
}
