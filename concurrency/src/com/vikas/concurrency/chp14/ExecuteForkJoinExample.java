package com.vikas.concurrency.chp14;

import java.util.concurrent.ForkJoinPool;

public class ExecuteForkJoinExample {

	public static void main(String[] args) {		
		
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		//SimpleRecursiveAction action = new SimpleRecursiveAction(300);
		//pool.invoke(action);
		
		SimpleRecursiveTask task = new SimpleRecursiveTask(240);
		System.out.println(pool.invoke(task));
	}

}
