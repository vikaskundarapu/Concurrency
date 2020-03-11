package com.vikas.geeksforgeeks.slippedcondition.problem;

public class SlippedConditionChecker {

	public static void main(String[] args) throws InterruptedException {

		Thread slippedThread = new Thread(new SlippedThread());
		Thread readerThread = new Thread(new ReaderThread());

		slippedThread.start();
		readerThread.start();
	}

}
