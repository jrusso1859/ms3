package org.jrusso.convert;

public class ConversionPair<L, R> {
	private L l;
	private R r;

	public ConversionPair(L l, R r) {
		this.l = l;
		this.r = r;
	}

	public L getInput() {
		return l;
	}

	public R getConversion() {
		return r;
	}

	public void setInput(L l) {
		this.l = l;
	}

	public void setConversion(R r) {
		this.r = r;
	}
	public String toString() {
		return String.valueOf(l) + ":" + String.valueOf(r);
	}
}
