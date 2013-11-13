package com.dominantfreq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TransformationPool {

	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	private TransformationPool() { /* No instantiation */
	}

	public static <T> List<T> execute(List<? extends Callable<T>> transformations) {
		List<T> results = new ArrayList<T>();
		try {
			List<Future<T>> tasks = EXECUTOR.invokeAll(transformations);
			for (Future<T> task : tasks) {
				results.add(task.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return results;
	}

	public static void shutdownNow() {
		EXECUTOR.shutdownNow();
	}

}
