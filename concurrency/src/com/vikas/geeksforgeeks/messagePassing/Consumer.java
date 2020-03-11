package com.vikas.geeksforgeeks.messagePassing;

import java.util.Vector;

public class Consumer implements Runnable {
	final static int MAX = 7;
	
	@Override
	public void run() {
		System.out.println("Consumer Thread");
		try {
			Thread.sleep(1000);
			consume();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private void consume() throws InterruptedException {
		while (true) {
			synchronized (SharedResource.queue) {
				if(SharedResource.size > 0 && SharedResource.size <= MAX) {
					System.out.println("Consuming element: "+SharedResource.size);
					SharedResource.queue.poll();
					SharedResource.size--;	
				}
				
			}
		}
	}

}
