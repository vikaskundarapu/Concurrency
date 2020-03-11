package com.vikas.geeksforgeeks.slippedcondition.solution;

/**
 * Any resources that a thread is going to access after checking the condition,
 * must be locked by the thread and should only be released after the work is
 * performed by the thread. All the access must be synchronized.
 * 
 * With respect to the problem above, the slipped conditions can be eliminated,
 * by locking the String object of the CommonResource class. In this scenario,
 * the thread first gains the access and locks the String and then tries to
 * process the String.
 */
public class SlippedConditionChecker {

	public static void main(String[] args) throws InterruptedException {

		Thread slippedThread = new Thread(new SlippedThread());
		Thread readerThread = new Thread(new ReaderThread());

		slippedThread.start();
		readerThread.start();
	}

}
