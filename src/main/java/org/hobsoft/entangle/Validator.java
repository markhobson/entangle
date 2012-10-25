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

import java.util.Collection;

/**
 * A validator that can validate an object.
 * 
 * @author Mark Hobson
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