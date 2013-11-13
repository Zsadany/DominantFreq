package com.dominantfreq.service;

import java.util.concurrent.Callable;

public abstract class CallableTransformation<S, T> implements Callable<T>, Transformation<S, T> {

	protected final S source;

	public CallableTransformation(final S source) {
		this.source = source;
	}

	@Override
	public T call() throws Exception {
		return transform(source);
	}

}
