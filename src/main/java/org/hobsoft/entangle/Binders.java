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
package org.hobsoft.entangle;

/**
 * Factory for creating binders.
 * 
 * @author Mark Hobson
 * @see Binder
 */
public final class Binders
{
	// TODO: provide convenience factory method to create Binder<Void>?
	
	// constructors -----------------------------------------------------------
	
	private Binders()
	{
		throw new AssertionError();
	}
	
	// public methods ---------------------------------------------------------
	
	/**
	 * Creates a new {@code Binder}.
	 * 
	 * @param <V>
	 *            the type of violation that the binder can produce
	 * @return the binder
	 */
	public static <V> Binder<V> newBinder()
	{
		return new DefaultBinder<V>();
	}
}
