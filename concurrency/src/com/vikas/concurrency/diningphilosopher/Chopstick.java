package com.vikas.concurrency.diningphilosopher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

	private int id;
	private Lock lock;

	Chopstick(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}

	public boolean isPickedUp(Philosopher philosopher, ChopstickState state) throws InterruptedException {
		if (this.lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(philosopher + " has picked up  " + state.toString() + " " + this + " ");
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void putDown(Philosopher philosopher, ChopstickState state) {
		this.lock.unlock();
		System.out.println(philosopher + "has put down " + state.toString() + " " + this);
	}

	@Override
	public String toString() {
		return "Chopstick with id=" + id;
	}

}
