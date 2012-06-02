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
import org.entangle.Binder.SourceValidatableBindingBuilder;

/**
 * 
 * 
 * @author Mark Hobson
 * @param <S>
 *            the source type of the binding being built
 * @param <V>
 *            the violations type produced by the binding being built
 */
class DefaultSourceValidatableBindingBuilder<S, V>
	extends DefaultConvertableBindingBuilder<S, S, V>
	implements SourceValidatableBindingBuilder<S, S, V>
{
	// constructors -----------------------------------------------------------
	
	public DefaultSourceValidatableBindingBuilder(Observable<S> source, BinderCallback<V> callback)
	{
		super(source, null, Converters.<S>identity(), callback);
	}
	
	// SourceValidatableBindingBuilder methods -------------------------------

	/**
	 * {@inheritDoc}
	 */
	public ConvertableBindingBuilder<S, S, V> checking(Validator<? super S, ? extends V> sourceValidator)
	{
		return new DefaultConvertableBindingBuilder<S, S, V>(getSource(), sourceValidator, getConverter(),
			getCallback());
	}
}
