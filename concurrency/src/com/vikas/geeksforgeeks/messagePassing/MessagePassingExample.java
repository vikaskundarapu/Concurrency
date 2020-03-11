package com.vikas.geeksforgeeks.messagePassing;

public class MessagePassingExample {

	public static void main(String[] args) {
		Thread producer = new Thread(new Producer());
		Thread consumer = new Thread(new Consumer());
		producer.start();
		consumer.start();
	}
	
	
}
