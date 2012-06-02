/*
 * $HeadURL: https://svn.iizuka.co.uk/common/binding/api/tags/1.1.0-beta-1/src/test/java/uk/co/iizuka/common/binding/DefaultBindingTest.java $
 * 
 * (c) 2011 IIZUKA Software Technologies Ltd.  All rights reserved.
 */
package uk.co.iizuka.common.binding;

import static org.junit.Assert.assertEquals;
import static uk.co.iizuka.common.binding.test.BindingAssert.assertBinding;
import static uk.co.iizuka.common.binding.test.TestConverters.toStringConverter;

import java.util.Collections;

import org.junit.Test;

import uk.co.iizuka.common.binding.test.TestValidators;

/**
 * Tests {@code DefaultBinding}.
 * 
 * @author Mark Hobson
 * @version $Id: DefaultBindingTest.java 97694 2012-01-11 10:07:06Z mark@IIZUKA.CO.UK $
 * @see DefaultBinding
 */
public class DefaultBindingTest extends BindingTest
{
	// TODO: pull up toString tests when format standardised
	// TODO: does it make sense to pull up constructor tests?
	
	// tests ------------------------------------------------------------------
	
	@Test
	public void newDefaultBinding()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Converter<String, String> converter = Converters.identity();
		
		DefaultBinding<String, String, String> binding = new DefaultBinding<String, String, String>(source, converter,
			target);
		
		assertBinding(source, converter, target, binding);
		assertEquals(Collections.emptyList(), binding.getValue());
	}
	
	@Test
	public void newDefaultBindingWithValidators()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Converter<String, String> converter = Converters.identity();
		Validator<String, String> sourceValidator = TestValidators.validString();
		Validator<String, String> targetValidator = TestValidators.validString();
		
		DefaultBinding<String, String, String> binding = new DefaultBinding<String, String, String>(source,
			sourceValidator, converter, targetValidator, target);
		
		assertBinding(source, sourceValidator, converter, targetValidator, target, binding);
		assertEquals(Collections.emptyList(), binding.getValue());
	}
	
	@Test(expected = NullPointerException.class)
	public void newDefaultBindingWithNullSource()
	{
		Observable<String> target = Observables.instance();
		Converter<String, String> converter = Converters.identity();

		new DefaultBinding<String, String, String>(null, converter, target);
	}
	
	@Test(expected = NullPointerException.class)
	public void newDefaultBindingWithNullTarget()
	{
		Observable<String> source = Observables.instance();
		Converter<String, String> converter = Converters.identity();
		
		new DefaultBinding<String, String, String>(source, converter, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void newDefaultBindingWithNullConverter()
	{
		Observable<String> source = Observables.instance();
		Observable<String> target = Observables.instance();
		
		new DefaultBinding<String, String, String>(source, null, target);
	}
	
	@Test
	public void toStringTest()
	{
		DefaultBinding<Object, Object, Object> binding = new DefaultBinding<Object, Object, Object>(
			Observables.instance(), Converters.identity(), Observables.instance());
		
		String expected = "uk.co.iizuka.common.binding.DefaultBinding["
			+ "source=" + binding.getSource() + ","
			+ "target=" + binding.getTarget() + "]";
		
		assertEquals(expected, binding.toString());
	}
	
	@Test
	public void toStringWithConverter()
	{
		Observable<Integer> source = Observables.instance();
		Observable<String> target = Observables.instance();
		Converter<Integer, String> converter = toStringConverter();
		
		DefaultBinding<Integer, String, String> binding = new DefaultBinding<Integer, String, String>(source,
			converter, target);
		
		String expected = "uk.co.iizuka.common.binding.DefaultBinding["
			+ "source=" + source + ","
			+ "target=" + target + ","
			+ "converter=" + converter + "]";
		
		assertEquals(expected, binding.toString());
	}
	
	// BindingTest methods ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected <S, T, V> Binding<S, T, V> createBinding(Observable<S> source, Validator<? super S, V> sourceValidator,
		Converter<S, T> converter, Validator<? super T, V> targetValidator, Observable<T> target)
	{
		return new DefaultBinding<S, T, V>(source, sourceValidator, converter, targetValidator, target);
	}
}
