package com.dominantfreq.service;

public interface Transformation<S, T> {

	T transform(S source);

}
