package com.vikas.concurrency.chp9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Counting semaphores are used to control the number of activities that can
 * access a certain resource or perform a given action at the same time.
 * Counting semaphores can be used to implement resource pools or to impose a
 * bound on a collection. A Semaphore manages a set of virtual permits; the
 * initial number of permits is passed to the Semaphore constructor. Activities
 * can acquire permits (as long as some remain) and release permits when they
 * are done with them. If no permit is available, acquire blocks until one is
 * (or until interrupted or the operation times out). The release method returns
 * a permit to the semaphore. A degenerate case of a counting semaphore is a
 * binary semaphore, a Semaphore with an initial count of one. A binary
 * semaphore can be used as a mutex with nonreentrant locking semantics; whoever
 * holds the sole permit holds the mutex.
 * 
 * Semaphores are useful for implementing resource pools such as database
 * connection pools. While it is easy to construct a fixed-sized pool that fails
 * if you request a resource from an empty pool, what you really want is to
 * block if the pool is empty and unblock when it becomes nonempty again. If you
 * initialize a Semaphore to the pool size, acquire a permit before trying to
 * fetch a resource from the pool, and release the permit after putting a
 * resource back in the pool, acquire blocks until the pool becomes nonempty.
 * This technique is used in the bounded buffer class in Chapter 12 of
 * Concurrency in Practice. (An easier way to construct a blocking object pool
 * would be to use a BlockingQueue to hold the pooled resources.) Similarly, you
 * can use a Semaphore to turn any collection into a blocking bounded
 * collection, as illustrated by BoundedHashSet in Listing 5.14. The semaphore
 * is initialized to the desired maximum size of the collection. The add
 * operation acquires a permit before adding the item into the underlying
 * collection. If the underlying add operation does not actually add anything,
 * it releases the permit immediately. Similarly, a successful remove operation
 * releases a permit, enabling more elements to be added. The underlying Set
 * implementation knows nothing about the bound; this is handled by
 * BoundedHashSet.
 */

enum Downloader {
	INSTANCE;
	// is the permit so only 3 threads are allowed at a time. true is a fairness
	// parameter ensures the longest waiting thread gets acquired the lock
	// preventing starvation
	private Semaphore semaphore = new Semaphore(5, true);

	public void downloadData() {
		try {
			semaphore.acquire();
			download();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	private void download() {
		System.out.println("Downloading is in progress");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class SemphoreExample {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			service.execute(Downloader.INSTANCE::downloadData);
		}
		service.shutdown();
	}

}
