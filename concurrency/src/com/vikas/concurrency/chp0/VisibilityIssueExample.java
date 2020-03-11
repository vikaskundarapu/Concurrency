package com.vikas.concurrency.chp0;

import java.math.BigInteger;

/**
 * In a single-threaded environment, if you write a value to a variable and
 * later read that variable with no intervening writes, you can expect to get
 * the same value back. This seems only natural. It may be hard to accept at
 * first, but when the reads and writes occur in different threads, this is
 * simply not the case. In general, there is no guarantee that the reading
 * thread will see a value written by another thread on a timely basis, or even
 * at all. In order to ensure visibility of memory writes across threads, you
 * must use synchronization. Two threads, the main thread and the reader thread,
 * access the shared variables ready and number. The main thread starts the
 * reader thread and then sets number to 42 and ready to true. The reader thread
 * spins until it sees ready is true, and then prints out number. While it may
 * seem obvious that NoVisibility will print 42, it is in fact possible that it
 * will print zero, or never terminate at all! Because it does not use adequate
 * synchronization, there is no guarantee that the values of ready and number
 * written by the main thread will be visible to the reader thread. The class
 * VisibilityIssueExample could loop forever because the value of ready might
 * never become visible to the reader thread. Even more strangely, NoVisibility
 * could print zero because the write to ready might be made visible to the
 * reader thread before the write to number, a phenomenon known as reordering.
 * There is no guarantee that operations in one thread will be performed in the
 * order given by the program, as long as the reordering is not detectable from
 * within that thread—even if the reordering is apparent to other threads. When
 * the main thread writes first to number and then to ready without
 * synchronization, the reader thread could see those writes happen in the
 * opposite order—or not at all. a. When the reader thread examines ready, it
 * may see an out-of-date value. Unless synchronization is used every time a
 * variable is accessed, it is possible to see a stale value for that variable.
 * Worse, staleness is not all-or-nothing: a thread can see an up-to-date value
 * of one variable but a stale value of another variable that was written first.
 * When food is stale, it is usually still edible—just less enjoyable. But stale
 * data can be more dangerous. While an out-of-date hit counter in a web
 * application might not be so bad,2 stale values can cause serious safety or
 * liveness failures. In NoVisibility, stale values could cause it to print the
 * wrong value or prevent the program from terminating. Things can get even more
 * complicated with stale values of object references, such as the link pointers
 * in a linked list implementation. Stale data can cause serious and confusing
 * failures such as unexpected exceptions, corrupted data structures, inaccurate
 * computations, and infinite loops. MutableInteger in Listing 3.2 is not
 * thread-safe because the value field is accessed from both get and set without
 * synchronization. Among other hazards, it is susceptible to stale values: if
 * one thread calls set, other threads calling get may or may not see that
 * update. We can make MutableInteger thread safe by synchronizing the getter
 * and setter as shown in SynchronizedInteger in Listing 3.3. Synchronizing only
 * the setter would not be sufficient: threads calling get would still be able
 * to see stale values. When a thread reads a variable without synchronization,
 * it may see a stale value, but at least it sees a value that was actually
 * placed there by some thread rather than some random value. This safety
 * guarantee is called out-of-thin-air safety. Out-of-thin-air safety applies to
 * all variables, with one exception: 64-bit numeric variables (double and long)
 * that are not declared volatile (see Section 3.1.4). The Java Memory Model
 * requires fetch and store operations to be atomic, but for nonvolatile long
 * and double variables, the JVM is permitted to treat a 64-bit read or write as
 * two separate 32-bit operations. If the reads and writes occur in different
 * threads, it is therefore possible to read a nonvolatile long and get back the
 * high 32 bits of one value and the low 32 bits of another.3 Thus, even if you
 * don’t care about stale values, it is not safe to use shared mutable long and
 * double variables in multithreaded programs unless they are declared volatile
 * or guarded by a lock.
 **/
public class VisibilityIssueExample {

	private static boolean ready;
	private static long number;

	public static void main(String[] args) throws InterruptedException {
		new Thread(new ReaderThread()).start();
		number = 42;
		ready = true;
	}

	static class ReaderThread implements Runnable {

		@Override
		public void run() {

			while (!ready) {
				Thread.yield();
			}
			System.out.println("Number " + number);
		}
	}
}

class MutableInteger {
	private int value;

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}
}

class SynchronizedInteger {
	private int value;
	BigInteger bigInteger;

	public synchronized int get() {
		return value;
	}

	public synchronized void set(int value) {
		this.value = value;
	}
}