package org.jrusso.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

public class Converter implements Callable {

	private static Logger logger = Logger.getLogger(Converter.class);
	private ConversionResult conversionResult = new ConversionResult();

	public void convertRange(int rangeValue1, int rangeValue2) {
		conversionResult.setRangeValue1(rangeValue1);
		conversionResult.setRangeValue2(rangeValue2);
		convertRange();
	}

	public void convertRange() {
		if (conversionResult.getRangeValue1() == conversionResult.getRangeValue2()) {
			List<ConversionPair<Integer, String>> conversions = new ArrayList<>();
			int i = conversionResult.getRangeValue1();
			conversions.add(new ConversionPair<Integer, String>(Integer.valueOf(i), convert(i)));
			conversionResult.setConversions(conversions);
		} else {
			buildConversionPairList();
		}
	}

	private int determineIncrement(int rangeValue1, int rangeValue2) {
		if (rangeValue1 < rangeValue2) {
			return 1;
		} else if (rangeValue1 > rangeValue2) {
			return -1;
		} else {
			throw new UnsupportedOperationException("Asking an increment when rangeValue1 equals rangeValue2.");
		}
	}

	private void buildConversionPairList() {
		int rangeValue1 = conversionResult.getRangeValue1();
		int rangeValue2 = conversionResult.getRangeValue2();
		boolean reverse = conversionResult.isReverse();
		if (reverse) {
			int temp = rangeValue1;
			rangeValue1 = rangeValue2;
			rangeValue2 = temp;
		}
		int increment = determineIncrement(rangeValue1, rangeValue2);
		List<ConversionPair<Integer, String>> conversions = new ArrayList<>();
		if (increment < 0) {
			for (int i = rangeValue1; i >= rangeValue2; i += increment) {
				conversions.add(new ConversionPair<Integer, String>(Integer.valueOf(i), convert(i)));
			}
		} else {
			for (int i = rangeValue1; i <= rangeValue2; i += increment) {
				conversions.add(new ConversionPair<Integer, String>(Integer.valueOf(i), convert(i)));
			}
		}
		conversionResult.setConversions(conversions);
	}

	private String convert(int input) {
		if (input % 21 == 0) {
			return "MS3 and ME";
		} else if (input % 7 == 0) {
			return "MS3";
		} else if (input % 3 == 0) {
			return "ME";
		} else {
			return String.valueOf(input);
		}
	}

	public String conversionString(int rv1, int rv2) {
		convertRange(rv1, rv2);
		return conversionResult.getConversions().toString();
	}

	public ConversionResult getConversionResult() {
		return conversionResult;
	}

	private boolean canBeInteger(String input) {
		return input != null && !input.trim().isEmpty();
	}

	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage muleMessage = eventContext.getMessage();
		Map<String, String> parms = muleMessage.getInboundProperty("http.query.params");

		String reverse = parms.get("reverse");
		if ("on".equals(reverse)) {
			conversionResult.setReverse(true);
		}

		String rv1 = parms.get("rangeValue1");
		String rv2 = parms.get("rangeValue2");
		logger.info("Convert " + rv1 + " to " + rv2 + ".");
		if (canBeInteger(rv1) && canBeInteger(rv2)) {
			convertRange(Integer.parseInt(rv1), Integer.parseInt(rv2));
		}
		return conversionResult;
	}
}
