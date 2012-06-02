/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/test/BindingAssert.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle.test;

import static org.junit.Assert.assertSame;

import org.entangle.Binding;
import org.entangle.Converter;
import org.entangle.Observable;
import org.entangle.Validator;


/**
 * Provides custom assertions for {@code Binding} implementations.
 * 
 * @author Mark Hobson
 * @version $Id: BindingAssert.java 97694 2012-01-11 10:07:06Z mark@IIZUKA.CO.UK $
 * @see Binding
 */
public final class BindingAssert
{
	// constructors -----------------------------------------------------------
	
	private BindingAssert()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	public static <S, T, V> void assertBinding(Observable<S> expectedSource, Converter<S, T> expectedConverter,
		Observable<T> expectedTarget, Binding<S, T, V> actual)
	{
		assertBinding(expectedSource, null, expectedConverter, null, expectedTarget, actual);
	}
	
	public static <S, T, V> void assertBinding(Observable<S> expectedSource,
		Validator<? super S, V> expectedSourceValidator, Converter<S, T> expectedConverter,
		Validator<? super T, V> expectedTargetValidator, Observable<T> expectedTarget, Binding<S, T, V> actual)
	{
		assertSame("source", expectedSource, actual.getSource());
		assertSame("sourceValidator", expectedSourceValidator, actual.getSourceValidator());
		assertSame("converter", expectedConverter, actual.getConverter());
		assertSame("targetValidator", expectedTargetValidator, actual.getTargetValidator());
		assertSame("target", expectedTarget, actual.getTarget());
	}
}
