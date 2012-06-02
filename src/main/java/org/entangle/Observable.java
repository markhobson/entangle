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

/**
 * A value whose changes can be observed.
 * 
 * @author Mark Hobson
 * @version $Id: Observable.java 96822 2011-12-16 13:09:13Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the type being observed
 */
public interface Observable<T>
{
	void addObservableListener(ObservableListener<T> listener);
	
	void removeObservableListener(ObservableListener<T> listener);
	
	T getValue();
	
	void setValue(T value);
}
