package com.vikas.concurrency.chp6;

/*
 * If count, count1 are instance variable of SynchronizedBlocks class then
 * making a method synchronized creates an intrinsic lock on the object of class
 * SynchronizedBlocks and incidentally has the following effects: First, it is
 * not possible for two invocations of synchronized methods on the same object
 * to interleave. When one thread is executing a synchronized method for an
 * object, all other threads that invoke synchronized methods for the same
 * object block (suspend execution) until the first thread is done with the
 * object i.e in our case if t1 executes add(), t2 cannot execute addAgain()
 * till t1 completes the processing of add() method. This is because the lock is
 * on the SynchronizedBlocks class. This is not the expected behavior in our
 * example as the add() and addAll() are independent of each other. In order to
 * make, t1 and t2 access add() and addAgain() individually, we create 2 objects
 * lock1 and lock2. Instead of synchronized methods we now use synchronized
 * blocks. Each of the blocks are created on lock1 and lock2 objects. Now, since
 * the synchronization is on different objects, t1 and t2 can call add() and
 * addAgain() methods individually.
 * 
 * Second, when a synchronized method exits, it automatically establishes a
 * happens-before relationship with any subsequent invocation of a synchronized
 * method for the same object. This guarantees that changes to the state of the
 * object are visible to all threads.
 * 
 */
public class SynchronizedBlocks {

	private static int count;
	private static int count1;

	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	private static synchronized void add() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count++;
	}

	private static synchronized void addAgain() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count1++;

	}

	private static void add1() {
		synchronized (lock2) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
	}

	private static void addAgain1() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (lock1) {
			count1++;
		}

	}

	public static void compute() {
		for (int i = 0; i < 10; i++) {
			add();
			addAgain();

		}
	}

	public static void compute1() {
		for (int i = 0; i < 10; i++) {
			add1();
			addAgain1();
		}
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		/*
		 * Thread t1 = new Thread(() -> compute()); Thread t2 = new Thread(() ->
		 * compute());
		 */
		Thread t1 = new Thread(() -> compute());
		Thread t2 = new Thread(() -> compute());
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
			e.printStackTrace();
		}

		long endTime = System.nanoTime();
		System.out.println("Count1 is :" + count + " :Count2 is " + count1);
		System.out.println("Completed in " + (endTime - startTime));
	}

}
