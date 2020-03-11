package com.vikas.concurrency.chp11;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A latch is a synchronizer that can delay the progress of threads until it
 * reaches its terminal state [CPJ 3.4.2]. A latch acts as a gate: until the
 * latch reaches the terminal state the gate is closed and no thread can pass,
 * and in the terminal state the gate opens, allowing all threads to pass. Once
 * the latch reaches the terminal state, it cannot change state again, so it
 * remains open forever. Latches can be used to ensure that certain activities
 * do not proceed until other one-time activities complete, such as:
 * 
 * • Ensuring that a computation does not proceed until resources it needs have
 * been initialized. For example: A simple binary (two-state) latch could be
 * used to indicate “Resource R has been initialized”, and any activity that
 * requires R would wait first on this latch.
 * 
 * • Ensuring that a service does not start until other services on which it
 * depends have started. Each service would have an associated binary latch;
 * starting service S would involve first waiting on the latches for other
 * services on which S depends, and then releasing the S latch after startup
 * completes so any services that depend on S can then proceed
 *
 * • Waiting until all the parties involved in an activity, for instance the
 * players in a multi-player game, are ready to proceed. In this case, the latch
 * reaches the terminal state after all the players are ready.
 * 
 * CountDownLatch is a flexible latch implementation that can be used in any of
 * these situations; it allows one or more threads to wait for a set of events
 * to occur. The latch state consists of a counter initialized to a positive
 * number, representing the number of events to wait for. The countDown method
 * decrements the counter, indicating that an event has occurred, and the await
 * methods wait for the counter to reach zero, which happens when all the events
 * have occurred. If the counter is nonzero on entry, await blocks until the
 * counter reaches zero, the waiting thread is interrupted, or the wait times
 * out.
 */

public class CountDownLatchExample {

	public static void main(String[] args) {
		example3();
		System.out.println();
	}

	/**
	 * Here, we are waiting till all the tasks are completed, only after that is
	 * sysout saying "All the prerequisites are completed" is printed
	 */
	public static void example3() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		CountDownLatch latch = new CountDownLatch(5);
		for (int i = 0; i < 5; i++) {
			service.execute(new Worker(i + 1, latch));
		}

		// await() ensures that all the jobs are completed before printing sysout
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All the prerequisites are completed");
		service.shutdown();

	}

}

class Worker implements Runnable {
	private int id;
	private CountDownLatch latch;

	public Worker(int id, CountDownLatch latch) {
		super();
		this.id = id;
		this.latch = latch;
	}

	@Override
	public void run() {
		doWork();
		latch.countDown();
	}

	private void doWork() {
		System.out.println("Thread with id: " + id + " is started");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
