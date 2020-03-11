package com.vikas.concurrency.chp2;

class Runner1 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runner 1: " + i);
		}
			
	}
}

class Runner2 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runner 2: " + i);
		}
	}
}

class Runner3 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++)
			System.out.println("Runner 3: " + i);
	}
}

class Runner4 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++)
			System.out.println("Runner 4: " + i);
	}
}

public class ThreadJoin {

	public static void main(String[] args) {
		/**
		 * We call join() method on the thread when we want the thread to complete its
		 * task(i.e till the thread dies). Only when the thread task is completed, other 
		 * subsequent operations are
		 * performed. For example. without the join() on t1 and t2, "Completed!" would
		 * be printed well before the tasks of t1 and t2 are completed. With join on
		 * both threads, message is printed only after all thread executions are
		 * completed
		 */
		Thread t1 = new Thread(new Runner1());
		Thread t2 = new Thread(new Runner2());
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Completed!");
	}

}
