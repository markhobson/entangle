/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/main/java/uk/co/iizuka/common/binding/HierarchicalCompositeObservable.java $
 * 
 * (c) 2012 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static uk.co.iizuka.common.binding.Utilities.checkNotNull;

import uk.co.iizuka.common.binding.Observables.ObservableFactory;

/**
 * 
 * 
 * @author Mark Hobson
 * @version $Id: HierarchicalCompositeObservable.java 97957 2012-01-24 12:31:57Z mark@IIZUKA.CO.UK $
 * @param <T>
 *            the parent type being observed
 * @param <U>
 *            the child type being observed
 */
class HierarchicalCompositeObservable<T, U> extends SwitchableObservable<U>
{
	// fields -----------------------------------------------------------------
	
	private final Observable<T> parent;
	
	private final ObservableFactory<T, U> childFactory;

	private final ObservableListener<T> parentListener = new ObservableListener<T>()
	{
		@Override
		public void valueChanged(ObservableEvent<T> event)
		{
			updateDelegate();
		}
	};
	
	// constructors -----------------------------------------------------------
	
	public HierarchicalCompositeObservable(Observable<T> parent, ObservableFactory<T, U> childFactory)
	{
		this.parent = checkNotNull(parent, "parent cannot be null");
		this.childFactory = checkNotNull(childFactory, "childFactory cannot be null");
		
		updateDelegate();
		
		parent.addObservableListener(parentListener);
	}
	
	// Object methods ---------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[parent=" + parent + ", childFactory=" + childFactory + "]";
	}
	
	// private methods --------------------------------------------------------
	
	private void updateDelegate()
	{
		T parentValue = parent.getValue();
		
		Observable<U> child = childFactory.createObservable(parentValue);
		
		if (child == null)
		{
			child = Observables.nullObservable();
		}
		
		setDelegate(child);
	}
}
