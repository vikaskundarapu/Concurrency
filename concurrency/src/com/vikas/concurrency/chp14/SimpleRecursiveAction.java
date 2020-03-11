package com.vikas.concurrency.chp14;

import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private int simpleValue;

	public SimpleRecursiveAction(int simpleValue) {
		this.simpleValue = simpleValue;
	}

	@Override
	protected void compute() {

		if (simpleValue <= 100) {
			System.out.println("No need of Parallel execution: " + simpleValue);
		} else {
			System.out.println("Start the parallel execution: " + simpleValue);
			SimpleRecursiveAction leftTask = new SimpleRecursiveAction(simpleValue / 2);
			SimpleRecursiveAction rightTask = new SimpleRecursiveAction(simpleValue / 2);

			leftTask.fork();
			System.out.println("After leftTask: "+simpleValue);
			//rightTask.compute(); // This is a better way to do the computation
			leftTask.fork();
			System.out.println("After rightTask:"+simpleValue);
		}

	}

}
