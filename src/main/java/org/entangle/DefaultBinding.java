/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DefaultBinding.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static org.entangle.Binding.Phase.CONVERT;
import static org.entangle.Binding.Phase.GET;
import static org.entangle.Binding.Phase.SET;
import static org.entangle.Binding.Phase.VALIDATE_POST_GET;
import static org.entangle.Binding.Phase.VALIDATE_PRE_SET;
import static org.entangle.Utilities.checkNotNull;
import static org.entangle.Utilities.checkState;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default {@code Binding} implementation.
 * 
 * @author Mark Hobson
 * @version $Id: DefaultBinding.java 101059 2012-05-03 13:39:52Z mark@IIZUKA.CO.UK $
 * @param <S>
 *            the source type of this binding
 * @param <T>
 *            the target type of this binding
 * @param <V>
 *            the violations type produced by this binding
 * @see Binding
 */
class DefaultBinding<S, T, V> implements Binding<S, T, V>
{
	// fields -----------------------------------------------------------------
	
	private final Observable<S> source;
	
	private final Validator<? super S, ? extends V> sourceValidator;
	
	private final Converter<S, T> converter;
	
	private final Validator<? super T, ? extends V> targetValidator;
	
	private final Observable<T> target;
	
	private final ObservableListener<S> sourceListener = new ObservableListener<S>()
	{
		@Override
		public void valueChanged(ObservableEvent<S> event)
		{
			pushUpTo(boundPhase);
		}
	};
	
	private final ObservableListener<T> targetListener = new ObservableListener<T>()
	{
		@Override
		public void valueChanged(ObservableEvent<T> event)
		{
			pullUpTo(boundPhase);
		}
	};
	
	private final ObservableSupport<Collection<V>> support;
	
	private final Collection<V> violations;
	
	private Phase boundPhase;
	
	// constructors -----------------------------------------------------------
	
	public DefaultBinding(Observable<S> source, Converter<S, T> converter, Observable<T> target)
	{
		this(source, null, converter, null, target);
	}

	public DefaultBinding(Observable<S> source, Validator<? super S, ? extends V> sourceValidator,
		Converter<S, T> converter, Validator<? super T, ? extends V> targetValidator, Observable<T> target)
	{
		this.source = checkNotNull(source, "source cannot be null");
		this.sourceValidator = sourceValidator;
		this.converter = checkNotNull(converter, "converter cannot be null");
		this.targetValidator = targetValidator;
		this.target = checkNotNull(target, "target cannot be null");
		
		support = new ObservableSupport<Collection<V>>(this);
		violations = new ArrayList<V>();
		boundPhase = null;
	}

	// Binding methods --------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind()
	{
		bindUpTo(SET);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bindUpTo(Phase phase)
	{
		checkUnbound();
		
		source.addObservableListener(sourceListener);
		target.addObservableListener(targetListener);
		
		boundPhase = phase;
		
		// push to synchronize values initially
		pushUpTo(phase);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unbind()
	{
		checkBound();
		
		source.removeObservableListener(sourceListener);
		target.removeObservableListener(targetListener);
		
		boundPhase = null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void push()
	{
		pushUpTo(SET);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushUpTo(Phase phase)
	{
		// get
		
		if (!phase.contains(GET))
		{
			return;
		}
		
		S sourceValue = source.getValue();
		
		// validate-post-get
		
		if (!phase.contains(VALIDATE_POST_GET))
		{
			return;
		}
		
		Collection<V> oldViolations = new ArrayList<V>(violations);
		violations.clear();
		
		if (validate(sourceValidator, sourceValue) && phase.contains(CONVERT))
		{
			// convert
			
			T targetValue = converter.convert(sourceValue);
		
			// validate-pre-set
			
			if (phase.contains(VALIDATE_PRE_SET) && validate(targetValidator, targetValue) && phase.contains(SET))
			{
				// set
				
				target.setValue(targetValue);
			}
		}
		
		support.fireValueChanged(oldViolations, new ArrayList<V>(violations));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pull()
	{
		pullUpTo(Phase.SET);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pullUpTo(Phase phase)
	{
		// get
		
		if (!phase.contains(GET))
		{
			return;
		}
		
		T targetValue = target.getValue();
		
		// validate-post-get
		
		if (!phase.contains(VALIDATE_POST_GET))
		{
			return;
		}
		
		Collection<V> oldViolations = new ArrayList<V>(violations);
		violations.clear();

		if (validate(targetValidator, targetValue) && phase.contains(CONVERT))
		{
			// convert
			
			boolean converted = true;
			S sourceValue = null;
			
			try
			{
				sourceValue = converter.unconvert(targetValue);
			}
			// TODO: catch all other exceptions and terminate too?
			catch (UnsupportedOperationException exception)
			{
				converted = false;
			}
			
			// validate-pre-set
		
			if (converted && phase.contains(VALIDATE_PRE_SET) && validate(sourceValidator, sourceValue)
				&& phase.contains(SET))
			{
				// set
				
				source.setValue(sourceValue);
			}
		}
		
		support.fireValueChanged(oldViolations, new ArrayList<V>(violations));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<S> getSource()
	{
		return source;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Validator<? super S, ? extends V> getSourceValidator()
	{
		return sourceValidator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter<S, T> getConverter()
	{
		return converter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Validator<? super T, ? extends V> getTargetValidator()
	{
		return targetValidator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<T> getTarget()
	{
		return target;
	}
	
	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObservableListener(ObservableListener<Collection<V>> listener)
	{
		support.addObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObservableListener(ObservableListener<Collection<V>> listener)
	{
		support.removeObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<V> getValue()
	{
		return violations;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Collection<V> value)
	{
		throw new UnsupportedOperationException("Binding violations are immutable");
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(getClass().getName());
		builder.append("[source=").append(source);
		builder.append(",target=").append(target);
		
		// TODO: remove unnecessary type argument when javac can cope
		if (converter != Converters.<S>identity())
		{
			builder.append(",converter=").append(converter);
		}
		
		builder.append("]");
		
		return builder.toString();
	}
	
	// private methods --------------------------------------------------------
	
	private void checkBound()
	{
		checkState(boundPhase != null, "Binding not bound");
	}
	
	private void checkUnbound()
	{
		checkState(boundPhase == null, "Binding already bound");
	}
	
	private <U> boolean validate(Validator<? super U, ? extends V> validator, U value)
	{
		if (validator != null)
		{
			Collection<? extends V> validatorViolations = validator.validate(value);
			
			if (validatorViolations != null)
			{
				violations.addAll(validatorViolations);
			}
		}
		
		return violations.isEmpty();
	}
}
