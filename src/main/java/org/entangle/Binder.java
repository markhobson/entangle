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
package org.entangle;

import java.util.List;

/**
 * Factory for creating bindings between observable values. To obtain implementations of this interface use
 * {@code Binders.newBinder()}.
 * <p>
 * This factory allows the following types of bindings to be constructed:
 * <h3>Bindings</h3>
 * To create a bidirectional binding between two values use:
 * <pre>
 * binder.bind(source).to(target);
 * </pre>
 * To create a bidirectional binding between two values that performs conversion use:
 * <pre>
 * binder.bind(source).using(converter).to(target);
 * </pre>
 * To create a bidirectional binding between two values that performs validation use:
 * <pre>
 * binder.bind(source).checking(validator).to(target);
 * </pre>
 * To create a bidirectional binding between two values that performs validation before conversion use:
 * <pre>
 * binder.bind(source).checking(validator).using(converter).to(target);
 * </pre>
 * To create a bidirectional binding between two values that performs validation after conversion use:
 * <pre>
 * binder.bind(source).using(converter).checking(validator).to(target);
 * </pre>
 * To create a bidirectional binding between two values that performs validation before and after conversion use:
 * <pre>
 * binder.bind(source).checking(sourceValidator).using(converter).checking(targetValidator).to(target);
 * </pre>
 * 
 * @author Mark Hobson
 * @param <V> 
 *            the type of violation that this binder can produce
 * @see Binders#newBinder()
 * @see Binding
 */
public interface Binder<V> extends Binding<List<Object>, List<Object>, V>
{
	// TODO: push and pull methods for unidirectional bindings, e.g. push(source).to(target), pull(target).from(source)
	// TODO: bind policy: automatic or manual
	// TODO: allow multiple using() for composite conversion?
	
	// types ------------------------------------------------------------------
	
	/**
	 * Part of the {@code Binder} fluent API.
	 * 
	 * @param <S>
	 *            the source type of the binding being built
	 * @param <T>
	 *            the target type of the binding being built
	 * @param <V>
	 *            the violations type produced by the binding being built
	 * @see Binder
	 */
	interface BindingBuilder<S, T, V>
	{
		/**
		 * ...with the value of the specified target.
		 * 
		 * @param target
		 *            the target whose value to bind with
		 * @return the binding
		 */
		Binding<S, T, V> to(Observable<T> target);
	}
	
	/**
	 * Part of the {@code Binder} fluent API.
	 * 
	 * @param <S>
	 *            the source type of the binding being built
	 * @param <T>
	 *            the target type of the binding being built
	 * @param <V>
	 *            the violations type produced by the binding being built
	 * @see Binder
	 */
	interface TargetValidatableBindingBuilder<S, T, V> extends BindingBuilder<S, T, V>
	{
		/**
		 * ...if it passes the specified validator...
		 * 
		 * @param targetValidator
		 *            the validator to check the value with, or {@code null} for none
		 * @return the binding builder
		 */
		BindingBuilder<S, T, V> checking(Validator<? super T, ? extends V> targetValidator);
	}
	
	/**
	 * Part of the {@code Binder} fluent API.
	 * 
	 * @param <S>
	 *            the source type of the binding being built
	 * @param <T>
	 *            the target type of the binding being built
	 * @param <V>
	 *            the violations type produced by the binding being built
	 * @see Binder
	 */
	interface ConvertableBindingBuilder<S, T, V> extends BindingBuilder<S, T, V>
	{
		/**
		 * ...by passing it through the specified converter...
		 * 
		 * @param <U>
		 *            the target type of the binding being built
		 * @param converter
		 *            the converter to transform the value with
		 * @return the binding builder
		 */
		<U> TargetValidatableBindingBuilder<S, U, V> using(Converter<S, U> converter);
	}
	
	/**
	 * Part of the {@code Binder} fluent API.
	 * 
	 * @param <S>
	 *            the source type of the binding being built
	 * @param <T>
	 *            the target type of the binding being built
	 * @param <V>
	 *            the violations type produced by the binding being built
	 * @see Binder
	 */
	interface SourceValidatableBindingBuilder<S, T, V> extends ConvertableBindingBuilder<S, T, V>
	{
		/**
		 * ...if it passes the specified validator...
		 * 
		 * @param sourceValidator
		 *            the validator to check the value with, or {@code null} for none
		 * @return the binding builder
		 */
		ConvertableBindingBuilder<S, T, V> checking(Validator<? super S, ? extends V> sourceValidator);
	}

	// public methods ---------------------------------------------------------

	/**
	 * Binds the value of the specified source...
	 * 
	 * @param <S>
	 *            the source type of the binding being built
	 * @param source
	 *            the source whose value to bind
	 * @return the binding builder
	 */
	<S> SourceValidatableBindingBuilder<S, S, V> bind(Observable<S> source);
	
	void add(Binder<V> binder);
}
