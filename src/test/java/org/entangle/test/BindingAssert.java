/*
 * Copyright 2012 IIZUKA Software Technologies Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
