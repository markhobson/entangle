/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/DefaultBinder.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Default {@code Binder} implementation.
 * 
 * @author Mark Hobson
 * @version $Id: DefaultBinder.java 98363 2012-02-09 15:01:09Z mark@IIZUKA.CO.UK $
 * @param <V>
 *            the type of violation that this binder can produce
 * @see Binder
 */
class DefaultBinder<V> implements Binder<V>, BinderCallback<V>
{
	// fields -----------------------------------------------------------------
	
	private final List<Binding<?, ?, V>> bindings;
	
	private final ObservableSupport<Collection<V>> support;
	
	private final List<Object> sources;
	
	private final List<Object> targets;
	
	private final Collection<V> violations;
	
	// types ------------------------------------------------------------------

	private abstract class AbstractBindingList<E> extends AbstractList<E>
	{
		// Collection methods -----------------------------------------------------
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int size()
		{
			return bindings.size();
		}
	}
	
	// constructors -----------------------------------------------------------
	
	public DefaultBinder()
	{
		bindings = new ArrayList<Binding<?, ?, V>>();
		support = new ObservableSupport<Collection<V>>(this);
		sources = createSources();
		targets = createTargets();
		violations = new ArrayList<V>();
	}

	// Binder methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <S> SourceValidatableBindingBuilder<S, S, V> bind(Observable<S> source)
	{
		return new DefaultSourceValidatableBindingBuilder<S, V>(source, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Binder<V> binder)
	{
		addBinding(binder);
	}
	
	// Binding methods --------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind()
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.bind();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bindUpTo(Phase phase)
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.bindUpTo(phase);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unbind()
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.unbind();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void push()
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.push();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushUpTo(Phase phase)
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.pushUpTo(phase);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pull()
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.pull();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pullUpTo(Phase phase)
	{
		for (Binding<?, ?, V> binding : bindings)
		{
			binding.pullUpTo(phase);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<List<Object>> getSource()
	{
		// TODO: list must fire change events when source observables change
		return Observables.instance(sources);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Validator<? super List<Object>, V> getSourceValidator()
	{
		// TODO: return composite validator
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Converter<List<Object>, List<Object>> getConverter()
	{
		// TODO: return composite converter
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Validator<? super List<Object>, V> getTargetValidator()
	{
		// TODO: return composite validator
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Observable<List<Object>> getTarget()
	{
		// TODO: list must fire change events when target observables change
		return Observables.instance(targets);
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
	public void setValue(Collection<V> violations)
	{
		throw new UnsupportedOperationException("Binder violations are immutable");
	}
	
	// BinderCallback methods -------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void built(Binding<?, ?, V> binding)
	{
		addBinding(binding);
	}

	// private methods --------------------------------------------------------
	
	private void addBinding(Binding<?, ?, V> binding)
	{
		bindings.add(binding);
		
		// prevent infinite loop when binder bound by itself
		if (binding.getSource() != this)
		{
			updateViolations();
			
			// TODO: aggregate violations better; ideally provide a live view
			binding.addObservableListener(new ObservableListener<Collection<V>>()
			{
				@Override
				public void valueChanged(ObservableEvent<Collection<V>> event)
				{
					updateViolations();
				}
			});
		}
	}
	
	private List<Object> createSources()
	{
		return new AbstractBindingList<Object>()
		{
			@Override
			public Object get(int index)
			{
				return bindings.get(index).getSource().getValue();
			}
		};
	}
	
	private List<Object> createTargets()
	{
		return new AbstractBindingList<Object>()
		{
			@Override
			public Object get(int index)
			{
				return bindings.get(index).getTarget().getValue();
			}
		};
	}
	
	private void updateViolations()
	{
		Collection<V> oldViolations = new ArrayList<V>(violations);
		
		violations.clear();
		
		for (Binding<?, ?, V> binding : bindings)
		{
			violations.addAll(binding.getValue());
		}
		
		support.fireValueChanged(oldViolations, new ArrayList<V>(violations));
	}
}
