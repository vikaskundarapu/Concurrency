package com.vikas.concurrency.chp14.mergesort;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MergeSortExample {

	public static int[] createRandom1DArrayOfLength(int arrayLength) {
		Random random = new SecureRandom();
		int resultArray[] = new int[arrayLength];
		for (int j = 0; j < arrayLength; j++) {
			resultArray[j] = random.nextInt(1000);
		}
		return resultArray;
	}

	public static void main(String[] args) {
		int inputArray[] = createRandom1DArrayOfLength(800000);
		int[] copiedArray = Arrays.copyOf(inputArray, inputArray.length);
		//displayArray(inputArray);
		sequentialRun(inputArray);
		//displayArray(inputArray);
		parallelRun(copiedArray);
		//displayArray(copiedArray);
	}

	public static void displayArray(int[] arrayToBeDisplayed) {
		System.out.println(Arrays.toString(arrayToBeDisplayed));
	}

	public static void sequentialRun(int[] inputArray) {
		long start = System.nanoTime();
		MergeSortUsingForkAndJoin task = new MergeSortUsingForkAndJoin(inputArray);
		task.mergeSortWrapper(inputArray);
		long end = System.nanoTime();
		System.out.println("Sequential Run took: " + (end - start) + " ns");
	}

	public static void parallelRun(int[] inputArray) {
		long start = System.nanoTime();
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		MergeSortUsingForkAndJoin task = new MergeSortUsingForkAndJoin(inputArray);
		pool.invoke(task);
		long end = System.nanoTime();
		System.out.println("Parallel   Run took: " + (end - start) + " ns");
	}
}
