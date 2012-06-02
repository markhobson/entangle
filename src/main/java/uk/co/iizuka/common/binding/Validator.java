/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/Validator.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import java.util.Collection;

/**
 * A validator that can validate an object.
 * 
 * @author Mark Hobson
 * @version $Id: Validator.java 96822 2011-12-16 13:09:13Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type this validator validates
 * @param <V>
 *            the violations type this validator produces
 */
public interface Validator<T, V>
{
	/**
	 * Validates the specified object and returns any violations.
	 * 
	 * @param object
	 *            the object to validate
	 * @return the violations, or an empty collection or {@code null} for none
	 */
	Collection<V> validate(T object);
}
