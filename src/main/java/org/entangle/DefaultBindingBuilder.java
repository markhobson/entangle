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
package org.entangle;

import static org.entangle.Utilities.checkNotNull;

import org.entangle.Binder.BindingBuilder;

/**
 * 
 * 
 * @author Mark Hobson
 * @param <S>
 *            the source type of the binding being built
 * @param <T>
 *            the target type of the binding being built
 * @param <V>
 *            the violations type produced by the binding being built
 */
class DefaultBindingBuilder<S, T, V> implements BindingBuilder<S, T, V>
{
	// fields -----------------------------------------------------------------
	
	private final Observable<S> source;
	
	private final Validator<? super S, ? extends V> sourceValidator;
	
	private final Converter<S, T> converter;
	
	private final Validator<? super T, ? extends V> targetValidator;
	
	private final BinderCallback<V> callback;
	
	// constructors -----------------------------------------------------------
	
	public DefaultBindingBuilder(Observable<S> source, Validator<? super S, ? extends V> sourceValidator,
		Converter<S, T> converter, Validator<? super T, ? extends V> targetValidator, BinderCallback<V> callback)
	{
		this.source = checkNotNull(source, "source cannot be null");
		this.sourceValidator = sourceValidator;
		this.converter = checkNotNull(converter, "converter cannot be null");
		this.targetValidator = targetValidator;
		this.callback = checkNotNull(callback, "callback cannot be null");
	}
	
	// BindingBuilder methods ---------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Binding<S, T, V> to(Observable<T> target)
	{
		Binding<S, T, V> binding = new DefaultBinding<S, T, V>(source, sourceValidator, converter, targetValidator,
			target);
		
		callback.built(binding);
		
		return binding;
	}
	
	// public methods ---------------------------------------------------------
	
	public Observable<S> getSource()
	{
		return source;
	}
	
	public Validator<? super S, ? extends V> getSourceValidator()
	{
		return sourceValidator;
	}
	
	public Converter<S, T> getConverter()
	{
		return converter;
	}
	
	public Validator<? super T, ? extends V> getTargetValidator()
	{
		return targetValidator;
	}
	
	public BinderCallback<V> getCallback()
	{
		return callback;
	}
}
