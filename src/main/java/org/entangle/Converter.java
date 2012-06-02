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

/**
 * A converter that can convert an object from one type to another and optionally back again.
 * 
 * @author Mark Hobson
 * @param <S>
 *            the type that this converter converts from
 * @param <T>
 *            the type that this converter converts to
 */
public interface Converter<S, T>
{
	/**
	 * Converts the specified object from {@code S} to {@code T}.
	 * 
	 * @param source
	 *            the object to convert
	 * @return the converted object
	 */
	T convert(S source);
	
	/**
	 * Converts the specified object from {@code T} to {@code S}.
	 * 
	 * @param target
	 *            the object to convert
	 * @return the converted object
	 * @throws UnsupportedOperationException
	 *             if this converter is not bidirectional
	 */
	S unconvert(T target);
}
