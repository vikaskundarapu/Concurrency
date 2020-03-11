package com.vikas.concurrency.chp8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A reentrant lock has the same basic behavior as we have seen for synchronized
 * blocks. Of course there are some extended features !!!
 * 
 * We can make a lock fair: prevent thread starvation Synchronized blocks are
 * unfair by default. For example: Lock lock = new new ReentrantLock(true);
 * 
 * We can check whether the given lock is held or not with reentrant locks. For
 * example: lock.tryLock()
 * 
 * We can get the list of threads waiting for the given lock with reentrant
 * locks. For example: getWaitingThreads() but for this to work you need to
 * extend ReentrantLock as getWaitingThreads() is a protected method
 * 
 * Synchronized blocks are nicer: we do not need the try-catch-finally block
 */
class Worker {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void producer() throws InterruptedException {

		lock.lock();
		try {
			System.out.println("Producer is ready");
			condition.await();
			System.out.println("Producer is ready AGAIN!!");
		} finally {
			lock.unlock();
		}

	}

	public void consumer() throws InterruptedException {
		lock.lock();
		try {
			Thread.sleep(2000);
			System.out.println("Consumer is ready");
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

}

public class ProducerConsumerLRL {

	public static void main(String[] args) {

		Worker worker = new Worker();
		Thread thread1 = new Thread(() -> {
			try {
				worker.producer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				worker.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
