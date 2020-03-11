package com.vikas.concurrency.chp8;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock interface imitates a synchronous blocking mechanism and wait()/notify()
 * are defined by using Conditions interface. await(), signal() and signalAll()
 * are analogous to the functioning of wait(), notify(), notifyAll()
 * 
 * Code between lock() and unblock() reflects a synchronous block. unblock() can
 * be called from anywhere for example add() method which is different from a
 * sync-block
 * 
 * The biggest advantage of Lock objects over implicit locks is their ability to
 * back out of an attempt to acquire a lock. The tryLock method backs out if the
 * lock is not available immediately or before a timeout expires (if specified).
 * The lockInterruptibly method backs out if another thread sends an interrupt
 * before the lock is acquired.
 * 
 * As a down-side all the code after lock() is acquired is to be surrounded in a
 * try, catch finally block. Once lock is acquired and an exception is thrown
 * before calling unblock() then the thread will be locked forever. Also,
 * unblock() should be invoked in finally block
 */
public class ReentrantLockExample {

	private static int counter;
	private static Lock lock = new ReentrantLock();

	/** Area between lock and unlock is equivalent of synchronized block */
	public static void increment() {
		lock.lock();

		try {
			for (int i = 0; i < 100000; i++) {
				counter++;
			}
		} finally {
			lock.unlock();
		}
	}

	public static void add() {
		lock.unlock();
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(ReentrantLockExample::increment);
		Thread t2 = new Thread(ReentrantLockExample::increment);

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Counter value = " + counter);
	}

}
