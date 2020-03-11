package com.vikas.geeksforgeeks.messagePassing;

public class Producer implements Runnable {

	static final int MAX_SIZE = 7;
	static int count = 0;

	@Override
	public void run() {
		System.out.println("Producer Thread");
		try {
			produce();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void produce() throws InterruptedException {
		while (true) {
			synchronized (SharedResource.queue) {
				if (SharedResource.size < MAX_SIZE) {
					System.out.println("Producing element: "+(SharedResource.size+1));
					SharedResource.queue.add(count);
					SharedResource.size++;
				}
			}
		}
	}

}
