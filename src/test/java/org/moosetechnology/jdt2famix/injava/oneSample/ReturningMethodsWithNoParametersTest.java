package org.moosetechnology.jdt2famix.injava.oneSample;

import static org.junit.Assert.*;

import org.junit.Test;
import org.moosetechnology.jdt2famix.samples.basic.ReturningMethodsWithNoParameters;
import org.moosetechnology.model.famix.Method;

public class ReturningMethodsWithNoParametersTest extends OneSampleTestCase {

	@Override
	protected Class<?> sampleClass() {
		return ReturningMethodsWithNoParameters.class;
	}
	
	@Test
	public void testMethods() {
		assertEquals(4, type.getMethods().size());
	}
	
	@Test
	public void testDeclaredType() {
		type.getMethods().stream().forEach(m -> assertNotNull(m.getDeclaredType()));
	}
	
	
	@Test
	public void testPublicString() {
		Method publicString = methodNamed("publicString");
		assertNotNull(publicString.getDeclaredType());
		assertEquals("String", publicString.getDeclaredType().getName());
	}
		
	@Test
	public void testPublicInt() {
		Method publicInt = methodNamed("publicInt");
		assertNotNull(publicInt.getDeclaredType());
		assertEquals("int", publicInt.getDeclaredType().getName());
	}

}
