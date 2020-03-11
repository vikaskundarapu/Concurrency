package com.vikas.geeksforgeeks.slippedcondition.problem;

/**
 * Slipped Condition is a special type of race condition that can occur in a
 * multithreaded application. In this, a thread is suspended after reading a
 * condition and before performing the activities related to it. It rarely
 * occurs, however, one must look for it if the outcome is not as expected.
 * 
 * Example: Suppose there are two thread A and thread B which want to process a
 * string S. Firstly, thread A is started, it checks if there are any more
 * characters left to process, initially the whole string is available for
 * processing, so the condition is true. Now, thread A is suspended and thread B
 * starts. It again checks the condition, which evaluates to true and then
 * processes the whole string S. Now, when thread A again starts execution, the
 * string S is completely processed by this time and hence an error occurs. This
 * is known as a slipped condition
 */
public class SlippedThread implements Runnable {

	@Override
	public void run() {

		System.out.println("Slipped thread is running!");

		if (CommonResource.pointer != CommonResource.resource.length()) {
			System.out.println("Characters left : " + (CommonResource.resource.length() - CommonResource.pointer));
		}

		while (CommonResource.pointer < CommonResource.resource.length()) {

			synchronized (this) {
				try {
					wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Slipped Processing char: " + CommonResource.resource.charAt(CommonResource.pointer));
			System.out.println("Characters left : " + (CommonResource.resource.length() - CommonResource.pointer));
			CommonResource.pointer++;
		}

	}

}
