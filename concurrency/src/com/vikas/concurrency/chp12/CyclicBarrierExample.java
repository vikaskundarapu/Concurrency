package com.vikas.concurrency.chp12;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * We have seen how latches can facilitate starting a group of related
 * activities or waiting for a group of related activities to complete. Latches
 * are single-use objects; once a latch enters the terminal state, it cannot be
 * reset. Barriers are similar to latches in that they block a group of threads
 * until some event has occurred [CPJ 4.4.3]. The key difference is that with a
 * barrier, all the threads must come together at a barrier point at the same
 * time in order to proceed. Latches are for waiting for events; barriers are
 * for waiting for other threads. A barrier implements the protocol some
 * families use to rendezvous during a day at the mall: “Everyone meet at
 * McDonald’s at 6:00; once you get there, stay there until everyone shows up,
 * and then we’ll figure out what we’re doing next.” CyclicBarrier allows a
 * fixed number of parties to rendezvous repeatedly at a barrier point and is
 * useful in parallel iterative algorithms that break down a problem into a
 * fixed number of independent subproblems. Threads call await when they reach
 * the barrier point, and await blocks until all the threads have reached the
 * barrier point. If all threads meet at the barrier point, the barrier has been
 * successfully passed, in which case all threads are released and the barrier
 * is reset so it can be used again. If a call to await times out or a thread
 * blocked in await is interrupted, then the barrier is considered broken and
 * all outstanding calls to await terminate with BrokenBarrierException. If the
 * barrier is successfully passed, await returns a unique arrival index for each
 * thread, which can be used to “elect” a leader that takes some special action
 * in the next iteration. CyclicBarrier also lets you pass a barrier action to
 * the constructor; this is a Runnable that is executed (in one of the subtask
 * threads) when the barrier is successfully passed but before the blocked
 * threads are released. We can reuse CyclicBarriers using reset() method
 */

public class CyclicBarrierExample {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		CyclicBarrier barrier = new CyclicBarrier(5, () -> System.out.println("After the taks are executed"));
		for (int i = 0; i < 5; i++) {
			service.execute(new Worker(i + 1, barrier));
		}
		service.shutdown();
	}

}

class Worker implements Runnable {

	private int id;
	private CyclicBarrier barrier;
	private Random random;

	public Worker(int id, CyclicBarrier barrier) {
		super();
		this.id = id;
		this.barrier = barrier;
		this.random = new Random();
	}

	@Override
	public void run() {
		doSomeWork();
	}

	private void doSomeWork() {
		System.out.println("Thread " + this.id + " is started");
		try {
			Thread.sleep(random.nextInt(3000));
			System.out.println("Thread with id: " + this.id + " is finished!");
			barrier.await();
			System.out.println("After the await is finished!");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + "]";
	}
}
