package com.vikas.concurrency.chp14.mergesort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortUsingForkAndJoin extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	int inputArray[];
	int start;
	int end;

	public MergeSortUsingForkAndJoin(int[] inputArray, int start, int end) {
		super();
		this.inputArray = inputArray;
		this.start = start;
		this.end = end;
	}

	public MergeSortUsingForkAndJoin(int[] inputArray) {
		this(inputArray, 0, inputArray.length - 1);
	}

	@Override
	protected void compute() {
		int middle = (end - start) / 2 + start;
		if (start >= end) {
			return;
		} else {
			MergeSortUsingForkAndJoin leftTask = new MergeSortUsingForkAndJoin(inputArray, start, middle);
			MergeSortUsingForkAndJoin righttTask = new MergeSortUsingForkAndJoin(inputArray, middle + 1, end);

			leftTask.fork();
			righttTask.fork();
			leftTask.join();
			righttTask.join();
			sequentialMergeAll();
		}

	}

/*	public static void main(String[] args) {
		int inputArray[] = MergeSortExample.createRandom1DArrayOfLength(8);
		MergeSortUsingForkAndJoin join = new MergeSortUsingForkAndJoin(inputArray);
		join.mergeSortWrapper(inputArray);
		System.out.println(Arrays.toString(inputArray));
	}*/

	public void mergeSortWrapper(int inputArray[]) {
		merge(inputArray, 0, inputArray.length - 1);
	}

	public void merge(int inputArray[], int start, int end) {
		if (start < end) {
			int middle = (end - start) / 2 + start;
			merge(inputArray, start, middle);
			merge(inputArray, middle + 1, end);
			sequentialmergeAll(inputArray, start, middle, end);
		}
	}

	public void sequentialMergeAll() {
		int temp[] = new int[inputArray.length];
		int middle = (end - start) / 2 + start;
		for (int i = start; i <= end; i++) {
			temp[i] = inputArray[i];
		}

		int leftCounter = start;
		int rightCounter = middle + 1;
		int wall = start;

		while ((leftCounter <= middle) && (rightCounter <= end)) {
			if (temp[leftCounter] <= temp[rightCounter]) {
				inputArray[wall++] = temp[leftCounter++];
			} else {
				inputArray[wall++] = temp[rightCounter++];
			}
		}

		while (leftCounter <= middle) {
			inputArray[wall++] = temp[leftCounter++];
		}

		while (rightCounter <= end) {
			inputArray[wall++] = temp[rightCounter++];
		}
	}

	public void sequentialmergeAll(int inputArray[], int start, int middle, int end) {
		int temp[] = new int[inputArray.length];
		for (int i = start; i <= end; i++) {
			temp[i] = inputArray[i];
		}

		int leftCounter = start;
		int rightCounter = middle + 1;
		int wall = start;

		while ((leftCounter <= middle) && (rightCounter <= end)) {
			if (temp[leftCounter] <= temp[rightCounter]) {
				inputArray[wall++] = temp[leftCounter++];
			} else {
				inputArray[wall++] = temp[rightCounter++];
			}
		}

		while (leftCounter <= middle) {
			inputArray[wall++] = temp[leftCounter++];
		}

		while (rightCounter <= end) {
			inputArray[wall++] = temp[rightCounter++];
		}
	}

}
