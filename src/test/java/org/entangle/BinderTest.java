/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/BinderTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package org.entangle;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import static org.entangle.test.TestConverters.integerToString;
import static org.entangle.test.TestConverters.stringToInteger;
import static org.entangle.test.TestObservableListeners.mockObservableListenerWithValueChanged;
import static org.entangle.test.TestObservables.observable;
import static org.entangle.test.TestObservables.observableString;
import static org.entangle.test.TestValidators.invalidInteger;
import static org.entangle.test.TestValidators.invalidString;
import static org.entangle.test.TestValidators.throwException;
import static org.entangle.test.TestValidators.validIf;
import static org.entangle.test.TestValidators.validInteger;
import static org.entangle.test.TestValidators.validNumber;
import static org.entangle.test.TestValidators.validString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


import java.util.Collections;

import org.entangle.Binder;
import org.entangle.Binders;
import org.entangle.Binding;
import org.entangle.Converter;
import org.entangle.Observable;
import org.entangle.Observables;
import org.entangle.test.TestConverters;
import org.entangle.test.TestValidators;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests {@code Binder} implementations.
 * 
 * @author Mark Hobson
 * @version $Id: BinderTest.java 101059 2012-05-03 13:39:52Z mark@IIZUKA.CO.UK $
 * @see Binder
 */
@RunWith(JMock.class)
public abstract class BinderTest
{
	// fields -----------------------------------------------------------------
	
	private Mockery mockery = new JUnit4Mockery();

	private Binder<String> binder;
	
	// public methods ---------------------------------------------------------
	
	@Before
	public final void setUpBinderTest()
	{
		binder = createBinder();
	}

	// bind().to() tests ------------------------------------------------------
	
	@Test
	public void bindToDoesNotPushValueInitially()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		
		binder.bind(source).to(target);
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindToDoesNotPushAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		binder.bind(source).to(target);
		source.setValue("a");
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindToDoesNotPullAutomatically()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		binder.bind(source).to(target);
		target.setValue("a");
		
		assertNull(source.getValue());
	}
	
	@Test
	public void bindToPushesValue()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		binder.bind(source).to(target);
		source.setValue("a");
		binder.push();
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void bindToPullsValue()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		binder.bind(source).to(target);
		target.setValue("a");
		binder.pull();
		
		assertEquals("a", source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindToWithSupertypeTargetDoesNotCompile()
	{
//		Observable<String> source = Observables.instance();
//		Observable<Object> target = Observables.instance();
//		
//		binder.bind(source).to(target);
	}

	// TODO: automate this test
	@Ignore
	@Test
	public void bindToWithSubtypeTargetDoesNotCompile()
	{
//		Observable<Object> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//	
//		// should not compile
//		binder.bind(source).to(target);
	}
	
	@Test(expected = NullPointerException.class)
	public void bindToWithNullSource()
	{
		binder.bind(null).to(Observables.instance());
	}
	
	@Test(expected = NullPointerException.class)
	public void bindToWithNullTarget()
	{
		binder.bind(Observables.instance()).to(null);
	}
	
	@Test
	public void bindToReturnsBinding()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<String, String, String> binding = binder.bind(source).to(target);
		assertNotNull(binding);
	}
	
	// bind().using().to() tests ----------------------------------------------
	
	@Test
	public void bindUsingToConvertsValue()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(integerToString()).to(target);
		binder.push();
		
		assertEquals("123", target.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingToWithSupertypeTargetDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<Object> target = Observables.instance();
//		
//		binder.bind(source).using(integerToString()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingToWithSubtypeTargetDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//
//		// should not compile
//		binder.bind(source).using(integerToCharSequence()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingToWithSupertypeConverterDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		binder.bind(source).using(numberToString()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingToWithSubtypeConverterDoesNotCompile()
	{
//		Observable<Number> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//
//		// should not compile
//		binder.bind(source).using(integerToString()).to(target);
	}
	
	@Test(expected = TestConverters.Exception.class)
	public void bindUsingToWithConverterThatThrowsException()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(TestConverters.<Integer, String>throwException()).to(target);
		binder.push();
	}
	
	@Test(expected = NullPointerException.class)
	public void bindUsingToWithNullConverter()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using((Converter<Integer, String>) null).to(target);
	}
	
	@Test
	public void bindUsingToReturnsBinding()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<Integer, String, String> binding = binder.bind(source).using(integerToString()).to(target);
		assertNotNull(binding);
	}
	
	// bind().checking().to() tests -------------------------------------------
	
	@Test
	public void bindCheckingToPushesValueWhenValid()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validString()).to(target);
		binder.push();
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void bindCheckingToDoesNotPushValueWhenInvalid()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(invalidString("e")).to(target);
		binder.push();
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindCheckingToWithSupertypeValidatorPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<Integer> target = Observables.instance();
		
		binder.bind(source).checking(validNumber()).to(target);
		binder.push();
		
		assertEquals((Integer) 123, target.getValue());
	}
	
	@Test
	public void bindCheckingToPullsValueWhenValid()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance("a");
		
		binder.bind(source).checking(validString()).to(target);
		binder.pull();
		
		assertEquals("a", source.getValue());
	}
	
	@Test
	public void bindCheckingToDoesNotPullValueWhenInvalid()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance("a");
		
		binder.bind(source).checking(invalidString("e")).to(target);
		binder.pull();
		
		assertNull(source.getValue());
	}
	
	@Test
	public void bindCheckingToWithSupertypeValidatorPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<Integer> target = Observables.instance(123);
		
		binder.bind(source).checking(validNumber()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindCheckingToWithSubtypeValidatorDoesNotCompile()
	{
//		Observable<Number> source = Observables.instance();
//		Observable<Number> target = Observables.instance();
//		
//		// should not compile
//		binder.bind(source).checking(validInteger()).to(target);
	}
	
	@Test(expected = TestValidators.Exception.class)
	public void bindCheckingToWithValidatorThatThrowsException()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(throwException()).to(target);
		binder.push();
	}
	
	@Test
	public void bindCheckingToWithNullValidator()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(null).to(target);
		binder.push();
		
		assertEquals("a", target.getValue());
	}
	
	@Test
	public void bindCheckingToReturnsBinding()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<String, String, String> binding = binder.bind(source).checking(invalidString("e")).to(target);
		assertNotNull(binding);
	}
	
	@Test
	public void bindCheckingToReturnsBindingThatProvidesViolationWhenInvalid()
	{
		Observable<String> source = Observables.instance("a");
		
		Binding<String, String, String> binding = binder.bind(source).checking(invalidString("e"))
			.to(observableString());
		binder.push();
		
		assertEquals(Collections.singletonList("e"), binding.getValue());
	}
	
	@Test
	public void bindCheckingToReturnsBindingThatProvidesSubtypeViolationWhenInvalid()
	{
		Observable<String> source = Observables.instance("a");

		Binder<Object> superbinder = Binders.newBinder();
		Binding<String, String, Object> binding = superbinder.bind(source).checking(invalidString("e"))
			.to(observableString());
		superbinder.push();
		
		assertEquals(Collections.singletonList("e"), binding.getValue());
	}
	
	@Test
	public void bindCheckingToBinderProvidesViolationWhenInvalid()
	{
		Observable<String> source = Observables.instance("a");
		
		binder.bind(source).checking(invalidString("e")).to(observableString());
		binder.push();
		
		assertEquals(singletonList("e"), binder.getValue());
	}
	
	@Test
	public void bindCheckingToBinderProvidesNoViolationsWhenInvalidAndThenValid()
	{
		Observable<String> source = Observables.instance("a");
		
		binder.bind(source).checking(validIf("b", "e")).to(observableString());
		binder.push();
		source.setValue("b");
		binder.push();
		
		assertEquals(emptyList(), binder.getValue());
	}
	
	@Test
	public void bindCheckingToBinderProvidesViolationsWhenInvalid()
	{
		Observable<String> source = Observables.instance("a");
		Observable<String> target = observable();
		
		binder.bind(source).checking(invalidString("e1")).to(target);
		binder.bind(source).checking(invalidString("e2")).to(target);
		binder.push();
		
		assertEquals(asList("e1", "e2"), binder.getValue());
	}
	
	// bind().checking().using().to() tests -----------------------------------
	
	@Test
	public void bindCheckingUsingToPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validInteger()).using(integerToString()).to(target);
		binder.push();

		assertEquals("123", target.getValue());
	}
	
	@Test
	public void bindCheckingUsingToDoesNotPushValueWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(invalidInteger("e")).using(integerToString()).to(target);
		binder.push();
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindCheckingUsingToWithSupertypeValidatorPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validNumber()).using(integerToString()).to(target);
		binder.push();
		
		assertEquals("123", target.getValue());
	}
	
	@Test
	public void bindCheckingUsingToPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(validInteger()).using(integerToString()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	@Test
	public void bindCheckingUsingToDoesNotPullValueWhenInvalid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(invalidInteger("e")).using(integerToString()).to(target);
		binder.pull();
		
		assertNull(source.getValue());
	}
	
	@Test
	public void bindCheckingUsingToWithSupertypeValidatorPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(validNumber()).using(integerToString()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindCheckingUsingToWithSubtypeValidatorDoesNotCompile()
	{
//		Observable<Number> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		// should not compile
//		binder.bind(source).checking(validInteger()).using(numberToString()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindCheckingUsingToWithSupertypeConverterDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		binder.bind(source).checking(validInteger()).using(numberToString()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindCheckingUsingToWithSubtypeConverterDoesNotCompile()
	{
//		Observable<Number> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		// should not compile
//		binder.bind(source).checking(validNumber()).using(integerToString()).to(target);
	}
	
	@Test
	public void bindCheckingUsingToReturnsBinding()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<Integer, String, String> binding = binder.bind(source).checking(invalidInteger("e"))
			.using(integerToString()).to(target);
		assertNotNull(binding);
	}
	
	@Test
	public void bindCheckingUsingToReturnsBindingThatProvidesViolationWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		
		Binding<Integer, String, String> binding = binder.bind(source).checking(invalidInteger("e"))
			.using(integerToString()).to(observableString());
		binder.push();
		
		assertEquals(singletonList("e"), binding.getValue());
	}
	
	@Test
	public void bindCheckingUsingToReturnsBindingThatProvidesSubtypeViolationWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		
		Binder<Object> superbinder = Binders.newBinder();
		Binding<Integer, String, Object> binding = superbinder.bind(source).checking(invalidInteger("e"))
			.using(integerToString()).to(observableString());
		superbinder.push();
		
		assertEquals(singletonList("e"), binding.getValue());
	}
	
	// bind().using().checking().to() tests -----------------------------------
	
	@Test
	public void bindUsingCheckingToPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(integerToString()).checking(validString()).to(target);
		binder.push();

		assertEquals("123", target.getValue());
	}
	
	@Test
	public void bindUsingCheckingToDoesNotPushValueWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(integerToString()).checking(invalidString("e")).to(target);
		binder.push();
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindUsingCheckingToPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).using(integerToString()).checking(validString()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	@Test
	public void bindUsingCheckingToDoesNotPullValueWhenInvalid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).using(integerToString()).checking(invalidString("e")).to(target);
		binder.pull();
		
		assertNull(source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingCheckingToWithSupertypeConverterDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		binder.bind(source).using(numberToString()).checking(validString()).to(target);
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingCheckingToWithSubtypeConverterDoesNotCompile()
	{
//		Observable<Number> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		// should not compile
//		binder.bind(source).using(integerToString()).checking(validString()).to(target);
	}
	
	@Test
	public void bindUsingCheckingToWithSupertypeValidatorPushesValueWhenValid()
	{
		Observable<String> source = Observables.instance("123");
		Observable<Integer> target = Observables.instance();
		
		binder.bind(source).using(stringToInteger()).checking(validNumber()).to(target);
		binder.push();
		
		assertEquals((Integer) 123, target.getValue());
	}
	
	@Test
	public void bindUsingCheckingToWithSupertypeValidatorPullsValueWhenValid()
	{
		Observable<String> source = Observables.instance();
		Observable<Integer> target = Observables.instance(123);
		
		binder.bind(source).using(stringToInteger()).checking(validNumber()).to(target);
		binder.pull();
		
		assertEquals("123", source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindUsingCheckingToWithSubtypeValidatorDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<CharSequence> target = Observables.instance();
//		
//		// should not compile
//		binder.bind(source).using(integerToCharSequence()).checking(validString()).to(target);
	}
	
	@Test(expected = TestValidators.Exception.class)
	public void bindUsingCheckingToWithValidatorThatThrowsException()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(integerToString()).checking(throwException()).to(target);
		binder.push();
	}
	
	@Test
	public void bindUsingCheckingToWithNullValidator()
	{
		Observable<Integer> source = Observables.instance(1);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).using(integerToString()).checking(null).to(target);
		binder.push();
		
		assertEquals("1", target.getValue());
	}
	
	@Test
	public void bindUsingCheckingToReturnsBinding()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<Integer, String, String> binding = binder.bind(source).using(integerToString())
			.checking(invalidString("e")).to(target);
		assertNotNull(binding);
	}
	
	@Test
	public void bindUsingCheckingToReturnsBindingThatProvidesViolationWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		
		Binding<Integer, String, String> binding = binder.bind(source).using(integerToString())
			.checking(invalidString("e")).to(observableString());
		binder.push();
		
		assertEquals(singletonList("e"), binding.getValue());
	}
	
	@Test
	public void bindUsingCheckingToReturnsBindingThatProvidesSubtypeViolationWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		
		Binder<Object> superbinder = Binders.newBinder();
		Binding<Integer, String, Object> binding = superbinder.bind(source).using(integerToString())
			.checking(invalidString("e")).to(observableString());
		superbinder.push();
		
		assertEquals(singletonList("e"), binding.getValue());
	}
	
	// bind().checking().using().checking().to() tests ------------------------
	
	@Test
	public void bindCheckingUsingCheckingToPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validInteger()).using(integerToString()).checking(validString()).to(target);
		binder.push();
		
		assertEquals("123", target.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToDoesNotPushValueWhenSourceInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(invalidInteger("e")).using(integerToString()).checking(validString()).to(target);
		binder.push();
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToDoesNotPushValueWhenTargetInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validInteger()).using(integerToString()).checking(invalidString("e")).to(target);
		binder.push();
		
		assertNull(target.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(validInteger()).using(integerToString()).checking(validString()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToDoesNotPullValueWhenSourceInvalid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(invalidInteger("e")).using(integerToString()).checking(validString()).to(target);
		binder.pull();
		
		assertNull(source.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToDoesNotPullValueWhenTargetInvalid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(validInteger()).using(integerToString()).checking(invalidString("e")).to(target);
		binder.pull();
		
		assertNull(source.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToWithSupertypeSourceValidatorPushesValueWhenValid()
	{
		Observable<Integer> source = Observables.instance(123);
		Observable<String> target = Observables.instance();
		
		binder.bind(source).checking(validNumber()).using(integerToString()).checking(validString()).to(target);
		binder.push();
		
		assertEquals("123", target.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToWithSupertypeSourceValidatorPullsValueWhenValid()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance("123");
		
		binder.bind(source).checking(validNumber()).using(integerToString()).checking(validString()).to(target);
		binder.pull();
		
		assertEquals((Integer) 123, source.getValue());
	}
	
	// TODO: automate this test
	@Ignore
	@Test
	public void bindCheckingUsingCheckingToWithSupertypeConverterDoesNotCompile()
	{
//		Observable<Integer> source = Observables.instance();
//		Observable<String> target = Observables.instance();
//		
//		binder.bind(source).checking(validInteger()).using(numberToString()).checking(validString()).to(target);
	}
	
	@Test
	public void bindCheckingUsingCheckingToWithSupertypeTargetValidatorPushesValueWhenValid()
	{
		Observable<String> source = Observables.instance("123");
		Observable<Integer> target = Observables.instance();
		
		binder.bind(source).checking(validString()).using(stringToInteger()).checking(validNumber()).to(target);
		binder.push();
		
		assertEquals((Integer) 123, target.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToWithSupertypeTargetValidatorPullsValueWhenValid()
	{
		Observable<String> source = Observables.instance();
		Observable<Integer> target = Observables.instance(123);
		
		binder.bind(source).checking(validString()).using(stringToInteger()).checking(validNumber()).to(target);
		binder.pull();
		
		assertEquals("123", source.getValue());
	}
	
	@Test
	public void bindCheckingUsingCheckingToReturnsBinding()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		Binding<Integer, String, String> binding = binder.bind(source).checking(invalidInteger("e"))
			.using(integerToString()).checking(invalidString("e")).to(target);
		assertNotNull(binding);
	}
	
	@Test
	public void bindCheckingUsingCheckingToReturnsBindingThatProvidesSourceViolationsOnlyWhenInvalid()
	{
		Observable<Integer> source = Observables.instance(123);
		
		Binding<Integer, String, String> binding = binder.bind(source).checking(invalidInteger("e1"))
			.using(integerToString()).checking(invalidString("e2")).to(observableString());
		binder.push();
		
		assertEquals(singletonList("e1"), binding.getValue());
	}
	
	// Observable tests -------------------------------------------------------
	
	@Test
	public void addObservableListenerReceivesEventWhenBindingViolationChanged()
	{
		Observable<String> source = Observables.instance();
		
		binder.bind(source).checking(validIf(null, "e")).to(observableString());

		binder.addObservableListener(mockObservableListenerWithValueChanged(mockery, binder,
			Collections.<String>emptyList(), singletonList("e")));
		
		source.setValue("a");
		binder.push();
	}
	
	// protected methods ------------------------------------------------------
	
	protected abstract <V> Binder<V> createBinder();
}
