/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DefaultSourceValidatableBindingBuilder.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import org.entangle.Binder.ConvertableBindingBuilder;
import org.entangle.Binder.SourceValidatableBindingBuilder;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: DefaultSourceValidatableBindingBuilder.java 101059 2012-05-03 13:39:52Z mark@IIZUKA.CO.UK $
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
	@Override
	public ConvertableBindingBuilder<S, S, V> checking(Validator<? super S, ? extends V> sourceValidator)
	{
		return new DefaultConvertableBindingBuilder<S, S, V>(getSource(), sourceValidator, getConverter(),
			getCallback());
	}
}
