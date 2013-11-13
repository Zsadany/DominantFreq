package com.dominantfreq.model.data;

public final class Complex {

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex I = new Complex(0, 1);

	private final Double re;
	private final Double im;

	public Complex(final double real) {
		this(real, 0.0);
	}

	public Complex(final double real, final double imaginary) {
		re = real;
		im = imaginary;
	}

	public Complex(final Complex c) {
		re = c.re;
		im = c.im;
	}

	public Complex add(final Complex c) {
		return new Complex(c.re + re, c.im + im);
	}

	public Complex add(final double real) {
		return new Complex(re + real, im);
	}

	public Complex sub(final Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	public Complex mul(final Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + c.re * im);
	}

	public Complex mul(final double real) {
		return new Complex(re * real, im * real);
	}

	public Complex div(final Complex c) {
		double denom = c.re * c.re + c.im * c.im;
		if (denom == 0)
			return new Complex(Double.NaN, Double.NaN);
		else
			return new Complex((re * c.re + im * c.im) / denom, (im * c.re - re * c.im) / denom);
	}

	public Complex div(final int divider) {
		return new Complex(re / divider, im / divider);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public static Complex exp(final Complex c) {
		return c.exp();
	}

	private Complex exp() {
		double r = Math.exp(this.re);
		return new Complex(r * Math.cos(this.im), r * Math.sin(this.im));
	}

	public double getReal() {
		return re;
	}

	public double getImaginary() {
		return im;
	}
}
