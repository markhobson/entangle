/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DefaultConvertableBindingBuilder.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import org.entangle.Binder.ConvertableBindingBuilder;
import org.entangle.Binder.TargetValidatableBindingBuilder;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: DefaultConvertableBindingBuilder.java 101059 2012-05-03 13:39:52Z mark@IIZUKA.CO.UK $
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
