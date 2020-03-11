package com.vikas.concurrency.chp7;

import java.util.ArrayList;
import java.util.List;

/**
 * When notify() is called after adding or removing an element, the control
 * doesn't shift to other method directly as the block in which it is
 * defined(i.e while loop) is not yet completed, so the looping continues till
 * wait() is encountered which now becomes the last statement after notify() in
 * the loop and control shifts to other method. This ensures that all the values
 * are first added and all of them are then removed
 */
class ProcessorProducerConsumer {

	private List<Integer> sharedData = new ArrayList<>();
	private static final int LIMIT = 5;
	private static final int BOTTOM = 0;
	private int value = 0;
	private Object lock = new Object();

	public void produce() throws InterruptedException {

		synchronized (lock) {
			while (true) {
				if (sharedData.size() == LIMIT) {
					System.out.println("Limit has been reached. Time to consume");
					lock.wait();
				} else {
					System.out.println("Adding an element to the list: " + ++value);
					sharedData.add(value);
					lock.notify();
				}
				Thread.sleep(400);
			}
		}

	}

	public void consume() throws InterruptedException {

		synchronized (lock) {

			while (true) {
				if (sharedData.size() == BOTTOM) {
					System.out.println("Data is empty. Need to add elements");
					lock.wait();
				} else {
					System.out.println("Removing element: " + sharedData.remove(--value));
					lock.notify();
				}
				Thread.sleep(400);
			}
		}
	}
}

public class ProducerConsumer {

	public static void main(String[] args) {

		ProcessorProducerConsumer processor = new ProcessorProducerConsumer();
		Thread thread1 = new Thread(() -> {
			try {
				processor.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread thread2 = new Thread(() -> {
			try {
				processor.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		thread1.start();
		thread2.start();
	}

}
