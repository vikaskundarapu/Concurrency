package com.vikas.concurrency.chp5;

public class InterruptExample implements Runnable {

	public static void displayNumbers() {
		for (int i = 0; i <= 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println(System.nanoTime());
			} catch (Exception e) {
				System.out.println("Interrupted by " + Thread.currentThread().getName() + "!!!");
			}
			System.out.println("The value is: " + i + ". Thread is :" + Thread.currentThread().getName());
		}
	}

	@Override
	public void run() {
		displayNumbers();
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new InterruptExample(), "Thread 1");
		Thread t2 = new Thread(new InterruptExample(), "Thread 2");

		t1.start();
		t2.start();
		
		try {
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t1.interrupt();
	}
}
