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

/**
 * {@code Observable} decorator that maintains registered listeners when delegate changed.
 * 
 * @author Mark Hobson
 * @param <T>
 *            the type being observed
 */
class SwitchableObservable<T> extends DelegatingObservable<T>
{
	// fields -----------------------------------------------------------------
	
	private final ObservableSupport<T> support;
	
	// constructors -----------------------------------------------------------
	
	public SwitchableObservable()
	{
		this(Observables.<T>nullObservable());
	}
	
	public SwitchableObservable(Observable<T> delegate)
	{
		super(delegate);
		
		support = new ObservableSupport<T>(this);
	}
	
	// Observable methods -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObservableListener(ObservableListener<T> listener)
	{
		support.addObservableListener(listener);
		
		super.addObservableListener(listener);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObservableListener(ObservableListener<T> listener)
	{
		support.removeObservableListener(listener);

		super.removeObservableListener(listener);
	}
	
	// DelegatingObservable methods -------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDelegate(Observable<T> delegate)
	{
		Observable<T> oldDelegate = getDelegate();
		super.setDelegate(delegate);
		delegateChanged(oldDelegate, getDelegate());
	}
	
	// private methods --------------------------------------------------------
	
	private void delegateChanged(Observable<T> oldDelegate, Observable<T> newDelegate)
	{
		removeObservableListeners(oldDelegate);
		
		addObservableListeners(newDelegate);

		support.fireValueChanged(oldDelegate.getValue(), newDelegate.getValue());
	}
	
	private void addObservableListeners(Observable<T> observable)
	{
		for (ObservableListener<T> listener : support.getObservableListeners())
		{
			observable.addObservableListener(listener);
		}
	}

	private void removeObservableListeners(Observable<T> observable)
	{
		for (ObservableListener<T> listener : support.getObservableListeners())
		{
			observable.removeObservableListener(listener);
		}
	}
}
