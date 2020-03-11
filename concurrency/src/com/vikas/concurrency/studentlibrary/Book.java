package com.vikas.concurrency.studentlibrary;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Book {

	private int id;
	private Lock lock;

	public Book(int id) {
		super();
		this.id = id;
		this.lock = new ReentrantLock();
	}

	@Override
	public String toString() {
		return "Book id=" + id;
	}

	public void read(Student student) throws InterruptedException {

		// Here if lock on the book object is available then immediately we get the lock
		// on object lock else the thread goes waits for it be scheduled. Read
		// documentation of lock()
		this.lock.lock();
		System.out.println(student + " is reading " + this);
		Thread.sleep(2000);
		this.lock.unlock();
		System.out.println(student + " has finished reading " + this);
	}

}
