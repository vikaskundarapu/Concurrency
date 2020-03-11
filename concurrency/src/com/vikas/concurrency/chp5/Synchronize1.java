package com.vikas.concurrency.chp5;

/*
 * Without the synchronized keyword on method incrementCounter, the values might differ(as increment of 
 * int/long is not an atomic operation). 
 * With synchronized we have restricted the access to counter variable one thread at a time.
 * So the output will always be consistent and correct. 
 * */
public class Synchronize1 {

	private static long counter = 0;

	public static synchronized void incrementCounter() {
		++counter;
	}

	public static void process() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; ++i)
					incrementCounter();
			}
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 100; ++i)
				incrementCounter();
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		process();
		System.out.println(counter);
	}

}
