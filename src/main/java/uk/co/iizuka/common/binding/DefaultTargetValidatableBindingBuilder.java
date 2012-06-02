/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DefaultTargetValidatableBindingBuilder.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import uk.co.iizuka.common.binding.Binder.BindingBuilder;
import uk.co.iizuka.common.binding.Binder.TargetValidatableBindingBuilder;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: DefaultTargetValidatableBindingBuilder.java 101059 2012-05-03 13:39:52Z mark@IIZUKA.CO.UK $
 * @param <S>
 *            the source type of the binding being built
 * @param <T>
 *            the target type of the binding being built
 * @param <V>
 *            the violations type produced by the binding being built
 */
class DefaultTargetValidatableBindingBuilder<S, T, V>
	extends DefaultBindingBuilder<S, T, V>
	implements TargetValidatableBindingBuilder<S, T, V>
{
	// constructors -----------------------------------------------------------
	
	public DefaultTargetValidatableBindingBuilder(Observable<S> source,
		Validator<? super S, ? extends V> sourceValidator, Converter<S, T> converter, BinderCallback<V> callback)
	{
		super(source, sourceValidator, converter, null, callback);
	}
	
	// TargetValidatableBindingBuilder methods --------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BindingBuilder<S, T, V> checking(Validator<? super T, ? extends V> targetValidator)
	{
		return new DefaultBindingBuilder<S, T, V>(getSource(), getSourceValidator(), getConverter(), targetValidator,
			getCallback());
	}
}
