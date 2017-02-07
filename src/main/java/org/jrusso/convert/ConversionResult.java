package org.jrusso.convert;

import java.util.ArrayList;
import java.util.List;

public class ConversionResult {
	
	private static final int LOW_LIMIT = 1;
	private static final int HIGH_LIMIT = 200;
	
	private int rangeValue1;
	private int rangeValue2;
	private boolean reverse;
	private List<ConversionPair<Integer, String>> conversions = 
	  new ArrayList<>();
	
	public int getRangeValue1() {
		return rangeValue1;
	}
	public void setRangeValue1(int rangeValue1) {
		if (rangeValue1 < LOW_LIMIT || rangeValue1 > HIGH_LIMIT ) {
			throw new IllegalArgumentException("Value " + rangeValue1 + 
			  " is outside the allowed range, [" + LOW_LIMIT + " ... " + 
			  HIGH_LIMIT + "].");
		}
		this.rangeValue1 = rangeValue1;
	}
	public int getRangeValue2() {
		return rangeValue2;
	}
	public void setRangeValue2(int rangeValue2) {
		if (rangeValue2 < LOW_LIMIT || rangeValue2 > HIGH_LIMIT ) {
			throw new IllegalArgumentException("Value " + rangeValue2 + 
			  " is outside the allowed range, [" + LOW_LIMIT + " ... " + 
			  HIGH_LIMIT + "].");
		}
		this.rangeValue2 = rangeValue2;
	}
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	public List<ConversionPair<Integer, String>> getConversions() {
		return conversions;
	}
	public void setConversions(List<ConversionPair<Integer, String>> conversions) {
		this.conversions = conversions;
	}
}
