package com.vikas.geeksforgeeks.slippedcondition.problem;

public class ReaderThread implements Runnable {

	@Override
	public void run() {
		System.out.println("ReaderThread is running!");

		while (CommonResource.pointer < CommonResource.resource.length()) {
			System.out.println("Processing char: " + CommonResource.resource.charAt(CommonResource.pointer));
			System.out.println("Characters left : " + (CommonResource.resource.length() - CommonResource.pointer));
			CommonResource.pointer++;
		}

	}

}
