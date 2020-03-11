package com.vikas.concurrency.chp7;

/**
 * Once threads thread1 and thread2 start, thread1 invokes produce() and thread2
 * invokes consume() methods. Due to Thread.sleep(1000); in consume() the thread
 * is suspended for 1 second and produce() method is executed.
 * 
 * Note that the lock inside produce() and consume() is of the same class object
 * i.e Processor.class object.
 * 
 * thread1 acquires the lock on Processor.class and wait() method is called(the
 * lock is released now). Note that wait should always be called in a
 * synchronized context. Now, thread1 is suspended and the consume method gets
 * the lock on Processor.class and invokes notify() method which notifies the
 * waiting threads which need a lock on Processor.class object. Thus, produce()
 * method gets the access. Note that all the code in which notify() method is
 * called is executed and only after that the access goes to waiting threads. So
 * if notify() is the last statement of while loop then the iteration continues
 * till the loop gets completed/ or the thread is suspended
 */
class Processor {

	public void produce() throws InterruptedException {
		System.out.println("Produce method: Before wait");

		/* The lock is on the class object i.e on Processor.class object */
		synchronized (this) {
			/* waits only for 10 seconds and then reacquires the lock */
			wait(10000);
			System.out.println("Produce method: After wait");
		}
	}

	public void consume() throws InterruptedException {

		// This ensure that thread1 is always executed first since the second thread
		// will be suspended for 1 second
		Thread.sleep(1000);
		System.out.println("Consume method: Before notify");
		synchronized (this) {
			// waits only for 10 seconds and then re-acquires the lock
			notify();
			System.out.println("Consume method: After notify");
		}
	}
}

public class WaitAndNotifyExample {

	public static void main(String[] args) {

		Processor processor = new Processor();
		Thread thread1 = new Thread(() -> {
			try {
				processor.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				processor.consume();
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
