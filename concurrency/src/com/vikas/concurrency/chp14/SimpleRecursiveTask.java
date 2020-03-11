package com.vikas.concurrency.chp14;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.concurrent.RecursiveTask;

public class SimpleRecursiveTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private final int simpleRecursiveValue;
	private static final int THRESHOLD = 100;

	public SimpleRecursiveTask(int simpleRecursiveValue) {
		super();
		this.simpleRecursiveValue = simpleRecursiveValue;
	}

	@Override
	protected Integer compute() {

		if (simpleRecursiveValue <= THRESHOLD) {
			System.out.println("No need of parallel execution:: " + simpleRecursiveValue);
			return 2 * simpleRecursiveValue;
		} else {
			System.out.println("We need to fork now :: " + simpleRecursiveValue);
			SimpleRecursiveTask leftTask = new SimpleRecursiveTask(simpleRecursiveValue / 2);
			SimpleRecursiveTask rightTask = new SimpleRecursiveTask(simpleRecursiveValue / 2);

			leftTask.fork();
			rightTask.fork();
			int result = 0;
			result += leftTask.join();
			result += rightTask.join();
			return result;
		}
	}

}
