package com.vikas.concurrency.diningphilosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosopherExample {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = null;
		Philosopher[] philosophers = null;

		try {
			service = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
			philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
			Chopstick[] chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

			for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
				chopsticks[i] = new Chopstick(i);
			}

			for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
				/* Modulo is needed for the right chop-stick of last philosopher */
				philosophers[i] = new Philosopher(i, chopsticks[i],
						chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
				service.execute(philosophers[i]);
			}

			/* To simulate for a certain duration as declared in Constants class */
			Thread.sleep(Constants.SIMULATION_TIME);

			/*
			 * Set the isFull flag to indicate the run() method needs to be completed. We
			 * have made this variable volatile so all the values are pickedUp from main
			 * memory
			 */
			for (Philosopher philosopher : philosophers) {
				philosopher.setFull(true);
			}
		} finally {
			service.shutdown();

			while (!service.isTerminated()) {
				Thread.sleep(1000);
			}

			for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
				System.out.println(
						"Philosopher " + philosophers[i] + " ate " + philosophers[i].getEatingCounter() + " times");
			}

		}

	}

}
