package com.vikas.geeksforgeeks.slippedcondition.solution;

public class ReaderThread implements Runnable {

	@Override
	public void run() {
		System.out.println("ReaderThread is running!");

		while (!CommonResource.isLocked && CommonResource.pointer < CommonResource.resource.length()) {
			CommonResource.isLocked = true;
			System.out.println("Processing char: " + CommonResource.resource.charAt(CommonResource.pointer));
			System.out.println("Characters left : " + (CommonResource.resource.length() - CommonResource.pointer));
			CommonResource.pointer++;
			CommonResource.isLocked = false;
		}

	}

}
