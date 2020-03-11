package com.vikas.concurrency.chp14.max;

import java.util.concurrent.RecursiveTask;

public class FindMaximumUsingForkJoin extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private final int[] inputArray;
	private final int start;
	private final int end;

	public FindMaximumUsingForkJoin(int[] inputArray, int start, int end) {
		super();
		this.inputArray = inputArray;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int length = end - start;
		if (length < MaximumExample.THRESHOLD) {
			return findMaximum(inputArray);
		} else {
			int middle = start + length / 2;
			FindMaximumUsingForkJoin leftTask = new FindMaximumUsingForkJoin(inputArray, start, middle);
			FindMaximumUsingForkJoin rightTask = new FindMaximumUsingForkJoin(inputArray, middle + 1, end);

			invokeAll(leftTask, rightTask);
			int result = Math.max(leftTask.join(), rightTask.join());
			return result;
		}
	}

	public int findMaximum(int inputArray[]) {
		int maximum = inputArray[start];
		for (int i = start + 1; i < end; i++) {
			if (maximum < inputArray[i]) {
				maximum = inputArray[i];
			}
		}
		return maximum;
	}

}
