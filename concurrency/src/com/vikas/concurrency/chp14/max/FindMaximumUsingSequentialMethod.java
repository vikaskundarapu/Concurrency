package com.vikas.concurrency.chp14.max;

public class FindMaximumUsingSequentialMethod {

	public static int findMaximum(int inputArray[]) {

		int maximum = inputArray[0];

		for (int i = 1; i < inputArray.length; i++) {
			if (maximum < inputArray[i]) {
				maximum = inputArray[i];
			}
		}

		return maximum;
	}
}
