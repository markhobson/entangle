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

import org.entangle.Binder.ConvertableBindingBuilder;
import org.entangle.Binder.TargetValidatableBindingBuilder;

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
class DefaultConvertableBindingBuilder<S, T, V>
	extends DefaultBindingBuilder<S, T, V>
	implements ConvertableBindingBuilder<S, T, V>
{
	// constructors -----------------------------------------------------------
	
	public DefaultConvertableBindingBuilder(Observable<S> source, Validator<? super S, ? extends V> sourceValidator,
		Converter<S, T> converter, BinderCallback<V> callback)
	{
		super(source, sourceValidator, converter, null, callback);
	}

	// ConvertableBindingBuilder methods --------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <U> TargetValidatableBindingBuilder<S, U, V> using(Converter<S, U> converter)
	{
		return new DefaultTargetValidatableBindingBuilder<S, U, V>(getSource(), getSourceValidator(), converter,
			getCallback());
	}
}
