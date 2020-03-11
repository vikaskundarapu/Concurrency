package com.vikas.concurrency.chp4;

public class Test {

	static boolean joy = false;

	public synchronized void guardedJoy() {
		// This guard only loops once for each special event, which may not
		// be the event we're waiting for.
		while (!joy) {
			try {
				System.out.println("WAIT");
				wait();
				System.out.println("AFTER WAIT");
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Joy and efficiency have been achieved!");
	}
	
	public synchronized void notifyJoy() {
	    joy = true;
	    notifyAll();
	}

	public static void main(String[] args) {

		Test test = new Test();
		Thread t1 = new Thread(() -> {
			test.guardedJoy();
		});

		t1.start();
		try {
			Thread.sleep(3000);
			joy=true;
			test.notifyJoy();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
