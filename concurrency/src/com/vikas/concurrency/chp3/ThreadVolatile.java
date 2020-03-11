package com.vikas.concurrency.chp3;

/**
 * Without volatile, there may cases where the loop inside run() method will run
 * infinitely as the isTerminated variable may be cached internally within the
 * processor. Thus, wont be able to pick the value set in the main method. With
 * volatile, all the values are picked from main memory or RAM. Instructions are
 * not reordered. Hence, volatile gives performance issues and should be used
 * cautiously. Also, certain primitives like float and double causes issues
 * because each instructions takes 2 clock cycles to perform certain operations
 * like add or subtract. With volatile, all the reads and writes are visible
 * only after complete operation is performed i.e intermittent/stale values are
 * not shown
 */

class Worker implements Runnable {

	private volatile boolean isTerminated = false;
	private static float count = 100000f;
	
	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	@Override
	public void run() {
		while (!isTerminated) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			System.out.println("Worker is running!  "+ ++count);
		}
	}
}

public class ThreadVolatile {
	public static void main(String[] args) {
		Worker worker = new Worker();
		Thread t1 = new Thread(worker);
		
		t1.start();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		worker.setTerminated(true);
	}

}
