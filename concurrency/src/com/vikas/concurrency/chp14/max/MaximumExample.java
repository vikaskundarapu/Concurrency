package com.vikas.concurrency.chp14.max;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MaximumExample {
	public static int THRESHOLD = 0;

	public static void main(String[] args) {
		int[] inputArray = create1DArrayOfLength(10000000);
		THRESHOLD = inputArray.length / Runtime.getRuntime().availableProcessors();
		FindMaximumUsingForkJoin forkJoin = new FindMaximumUsingForkJoin(inputArray, 0, inputArray.length);
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

		long start = System.nanoTime();
		System.out.println(pool.invoke(forkJoin));
		long end = System.nanoTime();
		System.out.println("Parallel   processing took: " + (end - start) + " ns");
		
		start = System.nanoTime();
		System.out.println(FindMaximumUsingSequentialMethod.findMaximum(inputArray));
		end = System.nanoTime();
		System.out.println("Sequential processing took: " + (end - start) + " ns");
		
	}

	private static int[] create1DArrayOfLength(int lengthOfArray) {
		Random random = new Random();
		int[] inputArray = new int[lengthOfArray];
		for (int j = 0; j < lengthOfArray; j++) {
			inputArray[j] = random.nextInt(1000);
		}
		return inputArray;
	}

}
