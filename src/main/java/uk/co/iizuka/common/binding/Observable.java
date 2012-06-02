/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Observable.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

/**
 * A value whose changes can be observed.
 * 
 * @author Mark Hobson
 * @version $Id: Observable.java 96822 2011-12-16 13:09:13Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type being observed
 */
public interface Observable<T>
{
	void addObservableListener(ObservableListener<T> listener);
	
	void removeObservableListener(ObservableListener<T> listener);
	
	T getValue();
	
	void setValue(T value);
}
