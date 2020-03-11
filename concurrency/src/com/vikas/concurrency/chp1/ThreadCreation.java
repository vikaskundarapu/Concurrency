package com.vikas.concurrency.chp1;

class Runner1 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++)
			System.out.println("Runner 1: " + i);
	}
}

class Runner2 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++)
			System.out.println("Runner 2: " + i);
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

/*
 * Each thread is associated with an instance of the class Thread. There are two
 * basic strategies for using Thread objects to create a concurrent application.
 * 
 * 1) To directly control thread creation and management, simply instantiate
 * Thread each time the application needs to initiate an asynchronous task. 2)
 * To abstract thread management from the rest of your application, pass the
 * application's tasks to an executor.
 * 
 * Thread can be created in 2 ways: 1) By implementing Runnable and providing
 * the implementation of run() method. Instantiate using Thread and pass the
 * class implementing Runnable as a parameter to the constructor and call
 * start() method. 2) Extend the Thread class and override run() method.
 * Instantiate the class(no need to create a Thread instance our class now
 * extends Thread) and call start() method
 */
public class ThreadCreation {

	public static void main(String[] args) {
		/* First method of creating threads */
		Thread t1 = new Thread(new Runner1());
		Thread t2 = new Thread(new Runner2());
		t1.start();
		t2.start();

		/* Second method of creating threads */
		Runner3 t3 = new Runner3();
		Runner4 t4 = new Runner4();
		t3.start();
		t4.start();
	}
}
