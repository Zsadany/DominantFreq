package com.dominantfreq.service.windowfunction;

public interface WindowFunction {

	/**
	 * Function returning the value of a window of a given length ( by a
	 * WindowFunction implementation ) at a given index.
	 */
	public double windowValueAt(final int index, final int length);

	/**
	 * Creates a window of the specified length.
	 */
	public double[] createWindowOf(final int length);

}
