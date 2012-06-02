/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/BindingTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static java.util.Collections.singletonList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static uk.co.iizuka.common.binding.Binding.Phase.CONVERT;
import static uk.co.iizuka.common.binding.Binding.Phase.GET;
import static uk.co.iizuka.common.binding.Binding.Phase.SET;
import static uk.co.iizuka.common.binding.Binding.Phase.VALIDATE_POST_GET;
import static uk.co.iizuka.common.binding.Binding.Phase.VALIDATE_PRE_SET;
import static uk.co.iizuka.common.binding.test.TestConverters.identityString;
import static uk.co.iizuka.common.binding.test.TestConverters.mockConverter;
import static uk.co.iizuka.common.binding.test.TestConverters.mockStringConverter;
import static uk.co.iizuka.common.binding.test.TestConverters.mockUnconverter;
import static uk.co.iizuka.common.binding.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static uk.co.iizuka.common.binding.test.TestObservables.mockSourceWithGetValue;
import static uk.co.iizuka.common.binding.test.TestObservables.mockSourceWithSetValue;
import static uk.co.iizuka.common.binding.test.TestObservables.mockStringSource;
import static uk.co.iizuka.common.binding.test.TestObservables.mockStringTarget;
import static uk.co.iizuka.common.binding.test.TestObservables.mockTargetWithGetValue;
import static uk.co.iizuka.common.binding.test.TestObservables.mockTargetWithSetValue;
import static uk.co.iizuka.common.binding.test.TestObservables.observableString;
import static uk.co.iizuka.common.binding.test.TestValidators.invalid;
import static uk.co.iizuka.common.binding.test.TestValidators.mockSourceValidator;
import static uk.co.iizuka.common.binding.test.TestValidators.mockTargetValidator;

import java.util.Collections;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.internal.StatePredicate;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@code Binding} implementations.
 * 
 * @author Mark Hobson
 * @version $Id: BindingTest.java 98365 2012-02-09 15:40:14Z mark@IIZUKA.CO.UK $
 * @see Binding
 */
@RunWith(JMock.class)
public abstract class BindingTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();
	
	private States states = mockery.states("binding");

	// tests ------------------------------------------------------------------
	
	@Test
	public void bindPushesValueInitially()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.bind();
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void bindPushesValueAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.bind();
		source.setValue("a");
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void bindPullsValueAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.bind();
		target.setValue("a");
		
		assertEquals("a", source.getValue());
	}
	
	@Test(expected = IllegalStateException.class)
	public void bindWhenAlreadyBound()
	{
		Binding<String, String, String> binding = createBinding();
		binding.bind();
		
		binding.bind();
	}
	
	@Test
	public void bindUpToGetPushesUpToPhaseAutomatically()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			source,
			ignoringWhenUnbound(mockSourceValidator(mockery)),
			ignoringWhenUnbound(mockStringConverter(mockery)),
			ignoringWhenUnbound(mockTargetValidator(mockery)),
			ignoringWhenUnbound(mockStringTarget(mockery))
		);

		binding.bindUpTo(GET);
		states.become("bound");
		source.setValue("a");
		
		// TODO: assert source.getValue invoked
	}
	
	@Test
	public void bindUpToGetPullsUpToPhaseAutomatically()
	{
		Observable<String> target = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			ignoringWhenUnbound(mockStringSource(mockery)),
			ignoringWhenUnbound(mockSourceValidator(mockery)),
			ignoringWhenUnbound(mockStringConverter(mockery)),
			ignoringWhenUnbound(mockTargetValidator(mockery)),
			target
		);

		binding.bindUpTo(GET);
		states.become("bound");
		target.setValue("a");
		
		// TODO: assert target.getValue invoked
	}
	
	@Test
	public void bindUpToValidatePostGetPushesUpToPhaseAutomatically()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			source,
			ignoringWhenUnbound(mockSourceValidator(mockery, "a")),
			ignoringWhenUnbound(mockStringConverter(mockery)),
			ignoringWhenUnbound(mockTargetValidator(mockery)),
			ignoringWhenUnbound(mockStringTarget(mockery))
		);
		
		binding.bindUpTo(VALIDATE_POST_GET);
		states.become("bound");
		source.setValue("a");
		
		// TODO: assert source.getValue invoked
	}
	
	@Test
	public void bindUpToValidatePostGetPullsUpToPhaseAutomatically()
	{
		Observable<String> target = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			ignoringWhenUnbound(mockStringSource(mockery)),
			ignoringWhenUnbound(mockSourceValidator(mockery)),
			ignoringWhenUnbound(mockStringConverter(mockery)),
			ignoringWhenUnbound(mockTargetValidator(mockery, "a")),
			target
		);
		
		binding.bindUpTo(VALIDATE_POST_GET);
		states.become("bound");
		target.setValue("a");
		
		// TODO: assert target.getValue invoked
	}
	
	@Test
	public void bindUpToConvertPushesUpToPhaseAutomatically()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			source,
			ignoringWhenUnbound(mockSourceValidator(mockery, "a")),
			ignoringWhenUnbound(mockConverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery)),
			ignoringWhenUnbound(mockStringTarget(mockery))
		);
		
		binding.bindUpTo(CONVERT);
		states.become("bound");
		source.setValue("a");
		
		// TODO: assert source.getValue invoked
	}
	
	@Test
	public void bindUpToConvertPullsUpToPhaseAutomatically()
	{
		Observable<String> target = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			ignoringWhenUnbound(mockStringSource(mockery)),
			ignoringWhenUnbound(mockSourceValidator(mockery)),
			ignoringWhenUnbound(mockUnconverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery, "a")),
			target
		);
		
		binding.bindUpTo(CONVERT);
		states.become("bound");
		target.setValue("a");
		
		// TODO: assert target.getValue invoked
	}
	
	@Test
	public void bindUpToValidatePreSetPushesUpToPhaseAutomatically()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			source,
			ignoringWhenUnbound(mockSourceValidator(mockery, "a")),
			ignoringWhenUnbound(mockConverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery, "b")),
			ignoringWhenUnbound(mockStringTarget(mockery))
		);
		
		binding.bindUpTo(VALIDATE_PRE_SET);
		states.become("bound");
		source.setValue("a");
		
		// TODO: assert source.getValue invoked
	}
	
	@Test
	public void bindUpToValidatePreSetPullsUpToPhaseAutomatically()
	{
		Observable<String> target = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			ignoringWhenUnbound(mockStringSource(mockery)),
			ignoringWhenUnbound(mockSourceValidator(mockery, "b")),
			ignoringWhenUnbound(mockUnconverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery, "a")),
			target
		);
		
		binding.bindUpTo(VALIDATE_PRE_SET);
		states.become("bound");
		target.setValue("a");
		
		// TODO: assert target.getValue invoked
	}
	
	@Test
	public void bindUpToSetPushesUpToPhaseAutomatically()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			source,
			ignoringWhenUnbound(mockSourceValidator(mockery, "a")),
			ignoringWhenUnbound(mockConverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery, "b")),
			ignoringWhenUnbound(mockTargetWithSetValue(mockery, "b"))
		);
		
		binding.bindUpTo(SET);
		states.become("bound");
		source.setValue("a");
		
		// TODO: assert source.getValue invoked
	}
	
	@Test
	public void bindUpToSetPullsUpToPhaseAutomatically()
	{
		Observable<String> target = Observables.instance();
		
		Binding<String, String, Object> binding = createBinding(
			ignoringWhenUnbound(mockSourceWithSetValue(mockery, "b")),
			ignoringWhenUnbound(mockSourceValidator(mockery, "b")),
			ignoringWhenUnbound(mockUnconverter(mockery, "a", "b")),
			ignoringWhenUnbound(mockTargetValidator(mockery, "a")),
			target);
		
		binding.bindUpTo(SET);
		states.become("bound");
		target.setValue("a");
		
		// TODO: assert target.getValue invoked
	}
	
	@Test
	public void unbindDoesNotPushAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.bind();
		binding.unbind();
		source.setValue("a");
		
		assertNull(target.getValue());
	}
	
	@Test
	public void unbindDoesNotPullAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.bind();
		binding.unbind();
		target.setValue("a");
		
		assertNull(source.getValue());
	}
	
	@Test(expected = IllegalStateException.class)
	public void unbindWhenUnbound()
	{
		Binding<String, String, String> binding = createBinding();
		
		binding.unbind();
	}
	
	@Test
	public void pushPropagatesValue()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.push();
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void pushUpToGet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithGetValue(mockery, "a"),
			mockSourceValidator(mockery),
			mockStringConverter(mockery),
			mockTargetValidator(mockery),
			mockStringTarget(mockery)
		);
		
		binding.pushUpTo(GET);
	}
	
	@Test
	public void pushUpToValidatePostGet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithGetValue(mockery, "a"),
			mockSourceValidator(mockery, "a"),
			mockStringConverter(mockery),
			mockTargetValidator(mockery),
			mockStringTarget(mockery)
		);
		
		binding.pushUpTo(VALIDATE_POST_GET);
	}
	
	@Test
	public void pushUpToConvert()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithGetValue(mockery, "a"),
			mockSourceValidator(mockery, "a"),
			mockConverter(mockery, "a", "b"),
			mockTargetValidator(mockery),
			mockStringTarget(mockery)
		);
		
		binding.pushUpTo(CONVERT);
	}
	
	@Test
	public void pushUpToValidatePreSet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithGetValue(mockery, "a"),
			mockSourceValidator(mockery, "a"),
			mockConverter(mockery, "a", "b"),
			mockTargetValidator(mockery, "b"),
			mockStringTarget(mockery)
		);
		
		binding.pushUpTo(VALIDATE_PRE_SET);
	}
	
	@Test
	public void pushUpToSet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithGetValue(mockery, "a"),
			mockSourceValidator(mockery, "a"),
			mockConverter(mockery, "a", "b"),
			mockTargetValidator(mockery, "b"),
			mockTargetWithSetValue(mockery, "b")
		);
		
		binding.pushUpTo(SET);
	}
	
	@Test
	public void pullPropagatesValue()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance("a");
		Binding<String, String, String> binding = createBinding(source, target);
		
		binding.pull();
		
		assertEquals("a", source.getValue());
	}
	
	@Test
	public void pullUpToGet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockStringSource(mockery),
			mockSourceValidator(mockery),
			mockStringConverter(mockery),
			mockTargetValidator(mockery),
			mockTargetWithGetValue(mockery, "a")
		);
		
		binding.pullUpTo(GET);
	}
	
	@Test
	public void pullUpToValidatePostGet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockStringSource(mockery),
			mockSourceValidator(mockery),
			mockStringConverter(mockery),
			mockTargetValidator(mockery, "a"),
			mockTargetWithGetValue(mockery, "a")
		);
		
		binding.pullUpTo(VALIDATE_POST_GET);
	}
	
	@Test
	public void pullUpToConvert()
	{
		Binding<String, String, Object> binding = createBinding(
			mockStringSource(mockery),
			mockSourceValidator(mockery),
			mockUnconverter(mockery, "a", "b"),
			mockTargetValidator(mockery, "a"),
			mockTargetWithGetValue(mockery, "a")
		);
		
		binding.pullUpTo(CONVERT);
	}
	
	@Test
	public void pullUpToValidatePreSet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockStringSource(mockery),
			mockSourceValidator(mockery, "b"),
			mockUnconverter(mockery, "a", "b"),
			mockTargetValidator(mockery, "a"),
			mockTargetWithGetValue(mockery, "a")
		);
		
		binding.pullUpTo(VALIDATE_PRE_SET);
	}
	
	@Test
	public void pullUpToSet()
	{
		Binding<String, String, Object> binding = createBinding(
			mockSourceWithSetValue(mockery, "b"),
			mockSourceValidator(mockery, "b"),
			mockUnconverter(mockery, "a", "b"),
			mockTargetValidator(mockery, "a"),
			mockTargetWithGetValue(mockery, "a")
		);
		
		binding.pullUpTo(SET);
	}
	
	@Test
	public void addObservableListenerReceivesEventWhenBindingViolationChanged()
	{
		Observable<String> source = Observables.instance();
		
		Binding<String, String, String> binding = createBinding(source, invalid("e"), identityString(), null,
			observableString());
		
		binding.addObservableListener(mockObservableListenerWithValueChanged(mockery, binding,
			Collections.<String>emptyList(), singletonList("e")));
		
		source.setValue("a");
		binding.push();
	}
	
	// protected methods ------------------------------------------------------
	
	protected abstract <S, T, V> Binding<S, T, V> createBinding(Observable<S> source,
		Validator<? super S, V> sourceValidator, Converter<S, T> converter, Validator<? super T, V> targetValidator,
		Observable<T> target);
	
	// private methods --------------------------------------------------------
	
	private <T> Binding<T, T, T> createBinding()
	{
		return createBinding(Observables.<T>instance(), Observables.<T>instance());
	}
	
	private <T, V> Binding<T, T, V> createBinding(Observable<T> source, Observable<T> target)
	{
		return createBinding(source, Converters.<T>identity(), target);
	}
	
	private <S, T, V> Binding<S, T, V> createBinding(Observable<S> source, Converter<S, T> converter,
		Observable<T> target)
	{
		return createBinding(source, null, converter, null, target);
	}
	
	private <T> T ignoringWhenUnbound(T mock)
	{
		return ignoring(mock, states.isNot("bound"));
	}
	
	private <T> T ignoring(final T mock, final StatePredicate when)
	{
		mockery.checking(new Expectations() { {
			ignoring(mock); when(when);
		} });
		
		return mock;
	}
}
