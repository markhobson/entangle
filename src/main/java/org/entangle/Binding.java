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

import java.util.Collection;

/**
 * A binding between two observable values.
 * 
 * @author Mark Hobson
 * @param <S>
 *            the source type of this binding
 * @param <T>
 *            the target type of this binding
 * @param <V> 
 *            the violations type produced by this binding
 */
public interface Binding<S, T, V> extends Observable<Collection<V>>
{
	// TODO: might need to reintroduce Observable<V> getViolations() if we wish to observe other properties such as
	// source and target
	
	// TODO: allow source/target to be bound/unbound individually
	
	/**
	 * Phase of value propagation within a binding.
	 */
	enum Phase
	{
		// constants --------------------------------------------------------------
		
		GET,
		// TODO: rename to POST_GET?
		VALIDATE_POST_GET,
		CONVERT,
		// TODO: rename to PRE_SET?
		VALIDATE_PRE_SET,
		SET;
		
		// public methods ---------------------------------------------------------
		
		public boolean contains(Phase phase)
		{
			return phase.ordinal() <= ordinal();
		}
	}

	// public methods ---------------------------------------------------------
	
	// TODO: introduce commented-out methods
	// TODO: consider introducing ObservableBinder { bind, bindUpTo, unbind } to chain methods, e.g. source().bind()
	
	void bind();
	
	void bindUpTo(Phase phase);
	
	void unbind();
	
//	void bindSource();
	
//	void bindSourceUpTo(Phase phase);
	
//	void unbindSource();
	
//	void bindTarget();
	
//	void bindTargetUpTo(Phase phase);
	
//	void unbindTarget();
	
	void push();
	
	void pushUpTo(Phase phase);
	
	void pull();
	
	void pullUpTo(Phase phase);
	
	Observable<S> getSource();
	
	Validator<? super S, ? extends V> getSourceValidator();
	
	Converter<S, T> getConverter();
	
	Validator<? super T, ? extends V> getTargetValidator();
	
	Observable<T> getTarget();
}
