package org.jrusso.convert.test;

import static org.junit.Assert.assertEquals;
import org.jrusso.convert.ConversionResult;
import org.jrusso.convert.Converter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConversionTests {

	private Converter converter;
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() {
		converter = new Converter();
	}
	@Test
	public void testZeroRanges() {
		assertEquals("[1:1]", converter.conversionString(1,1));
		assertEquals("[2:2]", converter.conversionString(2,2));
		assertEquals("[3:ME]", converter.conversionString(3,3));
		assertEquals("[4:4]", converter.conversionString(4,4));
		assertEquals("[5:5]", converter.conversionString(5,5));
		assertEquals("[6:ME]", converter.conversionString(6,6));
		assertEquals("[7:MS3]", converter.conversionString(7,7));
		assertEquals("[8:8]", converter.conversionString(8,8));
		assertEquals("[9:ME]", converter.conversionString(9,9));
	}
	
	@Test
	public void testLow() {
		exception.expect(IllegalArgumentException.class);
	    exception.expectMessage(
		  "Value 0 is outside the allowed range, [1 ... 200].");
		assertEquals("", converter.conversionString(0,7));
	}

	@Test
	public void testHigh() {
		exception.expect(IllegalArgumentException.class);
	    exception.expectMessage(
		  "Value 201 is outside the allowed range, [1 ... 200].");
		assertEquals("", converter.conversionString(199,201));
	}
	
	@Test
	public void testTwenties() {
		assertEquals("[20:20, 21:MS3 and ME, 22:22, 23:23, 24:ME, 25:25, 26:26, 27:ME, 28:MS3, 29:29]", 
		  converter.conversionString(20,29));
	}
	@Test
	public void testTwentiesReversed() {
		assertEquals("[29:29, 28:MS3, 27:ME, 26:26, 25:25, 24:ME, 23:23, 22:22, 21:MS3 and ME, 20:20]", 
		  converter.conversionString(29,20));
	}
	
	@Test
	public void testFortiesAndReversed() {
		assertEquals("[40:40, 41:41, 42:MS3 and ME, 43:43, 44:44, 45:ME, 46:46, 47:47, 48:ME, 49:MS3]", 
				  converter.conversionString(40,49));
		ConversionResult result = converter.getConversionResult();
		result.setReverse(true);
		converter.convertRange(40, 49);
		assertEquals("[49:MS3, 48:ME, 47:47, 46:46, 45:ME, 44:44, 43:43, 42:MS3 and ME, 41:41, 40:40]", 
				converter.conversionString(40,49));
		
	}
}
