package com.vikas.concurrency.chp9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The java.util.concurrent package defines three executor interfaces:
 * 
 * 1) Executor, a simple interface that supports launching new tasks.The
 * Executor interface provides a single method, execute, designed to be a
 * drop-in replacement for a common thread-creation idiom. If r is a Runnable
 * object, and e is an Executor object you can replace
 * 
 * (new Thread(r)).start(); with
 * 
 * e.execute(r);
 * 
 * The low-level idiom creates a new thread and launches it immediately.
 * Depending on the Executor implementation, threads are created. For example:
 * newCachedThreadPool(); etc
 * 
 * 
 * 
 * 2) ExecutorService, a sub-interface of Executor, which adds features that
 * help manage the life cycle, both of the individual tasks and of the executor
 * itself. The ExecutorService interface supplements execute with a similar, but
 * more versatile submit method. Like execute, submit accepts Runnable objects,
 * but also accepts Callable objects, which allow the task to return a value.
 * The submit method returns a Future object, which is used to retrieve the
 * Callable return value and to manage the status of both Callable and Runnable
 * tasks.
 * 
 * ExecutorService also provides methods for submitting large collections of
 * Callable objects. Finally, ExecutorService provides a number of methods for
 * managing the shutdown of the executor.
 * 
 * 3) ScheduledExecutorService, a sub-interface of ExecutorService, supports
 * future and/or periodic execution of tasks. The ScheduledExecutorService
 * interface supplements the methods of its parent ExecutorService with
 * schedule, which executes a Runnable or Callable task after a specified delay.
 * In addition, the interface defines scheduleAtFixedRate and
 * scheduleWithFixedDelay, which executes specified tasks repeatedly, at defined
 * intervals.
 * 
 * If we have to create multiple threads then creating them using Runnable
 * becomes difficult. We use ExecutorService in such cases which can be created
 * in three ways:
 * 
 * 1) ExecutorService service = Executors.newCachedThreadPool(); Here,
 * ExecutorService object returned dynamically reuses threads. Before starting a
 * job, there will be a check to see if any of threads have finished the job. If
 * there are threads that just finished the task then these threads are reused.
 * If there are no threads available then new thread is created to perform the
 * job. This executor is suitable for applications that launch many short-lived
 * tasks.
 * 
 * 2) ExecutorService service1 = Executors.newFixedThreadPool(40);
 * ExecutorService object returned maximizes the number of threads that needs to
 * be created. Before starting a job, there will be a check to see if any of
 * threads are available. If all threads are busy then service waits till any of
 * the threads terminate. NOTE: Here no new threads are created unlike
 * newCachedThreadPool()
 * 
 * 
 * 3) ExecutorService service2 = Executors.newSingleThreadExecutor();
 * ExecutorService object returned has only thread that performs the job
 * 
 * If none of the executors provided by the above factory methods meet your
 * needs, constructing instances of java.util.concurrent.ThreadPoolExecutor or
 * java.util.concurrent.ScheduledThreadPoolExecutor will give you additional
 * options
 * 
 * ExecutorService objects can call 2 methods: a) submit(): this method is
 * application for both Callable and Runnable implementations b) execute(): this
 * method is application for only Runnable implementations
 */

public class ExecutorServiceExample {

	public static void main(String[] args) {

		ExecutorService service = Executors.newCachedThreadPool();
		ExecutorService service1 = Executors.newFixedThreadPool(3);
		ExecutorService service2 = Executors.newSingleThreadExecutor();

		for (int i = 0; i < 5; i++) {
			// service.submit(new Worker());
			service1.submit(new Worker());
			// service2.submit(new Worker());
		}

		service.shutdown();
		service1.shutdown();
		service2.shutdown();
	}
}

class Worker implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}