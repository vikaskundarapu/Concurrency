package com.vikas.concurrency.chp4;

/**
 * The situation where each thread has a resource needed by another and is waiting for
 * a resource held by another, and will not release the one they hold until they
 * acquire the one they don’t, illustrates deadlock(5 Chinese philosophers and only 5 chop-sticks)
 * 
 * Deadlock is a situation in which two or more competing actions are each
 * waiting for the other to finish, and thus neither ever does. Deadlock
 * describes a situation where two or more threads are blocked forever, waiting
 * for each other. To prevent the deadlock in this example, wait till the
 * Alphonse has bowed and only then Gaston bows
 */
public class Deadlock {
	static class Friend {
		private final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.println(System.nanoTime());
			System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final Friend alphonse = new Friend("Alphonse");
		final Friend gaston = new Friend("Gaston");
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				alphonse.bow(gaston);
			}
		});
		thread1.start();
		// thread1.join();
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				gaston.bow(alphonse);
			}
		});
		thread2.start();
	}
}
