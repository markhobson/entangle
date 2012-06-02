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

import org.entangle.Binder;
import org.entangle.DefaultBinder;

/**
 * Tests {@code DefaultBinder}.
 * 
 * @author Mark Hobson
 * @version $Id: DefaultBinderTest.java 97099 2011-12-21 13:16:43Z mark@IIZUKA.CO.UK $
 * @see DefaultBinder
 */
public class DefaultBinderTest extends BinderTest
{
	// BinderTest methods -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <V> Binder<V> createBinder()
	{
		return new DefaultBinder<V>();
	}
}
