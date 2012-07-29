/*
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
package org.hobsoft.entangle.test;

import org.hobsoft.entangle.Binding;
import org.hobsoft.entangle.Converter;
import org.hobsoft.entangle.Observable;
import org.hobsoft.entangle.Validator;

import static org.junit.Assert.assertSame;

/**
 * Provides custom assertions for {@code Binding} implementations.
 * 
 * @author Mark Hobson
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
