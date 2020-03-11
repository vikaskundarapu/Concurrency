package com.vikas.concurrency.chp10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Tasks are logical units of work, and threads are a mechanism by which tasks
 * can run asynchronously.The primary abstraction for task execution in the Java
 * class libraries is not Thread, but Executor. The Executor framework uses
 * Runnable as its basic task representation. Runnable is a fairly limiting
 * abstraction; run cannot return a value or throw checked exceptions. Many
 * tasks are effectively deferred(postponed to a later time) computations, for
 * example: executing a database query, fetching a resource over the network, or
 * computing a complicated function. For these types of tasks, Callable is a
 * better abstraction: it expects that the main entry point, call, will return a
 * value(Future) and anticipates that it might throw an exception.To express a
 * non-value-returning task with Callable, use Callable<Void>.
 * 
 * So if we want to return something from the thread created, we have to use Callable as Runnable's run method returns nothing.
 */
class Worker implements Callable<String> {

	private int id;

	public Worker(int id) {
		this.id = id;
	}

	public Worker() {
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return "id= " + id;
	}
}

public class CallableFutureExample {

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(2);
		List<Future<String>> list = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			Future<String> future = service.submit(new Worker(i + 1));
			list.add(future);
		}

		for (Future<String> future : list) {
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		service.shutdown();
	}

}
