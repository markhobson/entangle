/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Converter.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

/**
 * A converter that can convert an object from one type to another and optionally back again.
 * 
 * @author Mark Hobson
 * @version $Id: Converter.java 97382 2011-12-28 20:21:54Z mark@IIZUKA.CO.UK $
 * @param <S>
 *            the type that this converter converts from
 * @param <T>
 *            the type that this converter converts to
 */
public interface Converter<S, T>
{
	/**
	 * Converts the specified object from {@code S} to {@code T}.
	 * 
	 * @param source
	 *            the object to convert
	 * @return the converted object
	 */
	T convert(S source);
	
	/**
	 * Converts the specified object from {@code T} to {@code S}.
	 * 
	 * @param target
	 *            the object to convert
	 * @return the converted object
	 * @throws UnsupportedOperationException
	 *             if this converter is not bidirectional
	 */
	S unconvert(T target);
}
