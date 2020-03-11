package com.vikas.concurrency.diningphilosopher;

import java.util.Random;

public class Philosopher implements Runnable {

	private int id;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private Random random;
	private int eatingCounter;
	private volatile boolean isFull = Boolean.FALSE;

	public Philosopher(int id, Chopstick righttChopstick, Chopstick leftChopstick) {
		super();
		this.id = id;
		this.rightChopstick = righttChopstick;
		this.leftChopstick = leftChopstick;
		this.random = new Random();
	}

	public void incrementCounter() {
		this.eatingCounter++;
	}

	public int getEatingCounter() {
		return this.eatingCounter;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public boolean getIsFull() {
		return this.isFull;
	}

	@Override
	public void run() {

		try {
			while (!isFull) {
				think();
				if (leftChopstick.isPickedUp(this, ChopstickState.LEFT)) {
					if (rightChopstick.isPickedUp(this, ChopstickState.RIGHT)) {
						eat();
						rightChopstick.putDown(this, ChopstickState.RIGHT);
					}
					leftChopstick.putDown(this, ChopstickState.LEFT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eat() throws InterruptedException {
		Thread.sleep(random.nextInt(1000));
		incrementCounter();
		System.out.println(this + " is eating");
	}

	private void think() throws InterruptedException {
		System.out.println(this + " is thinking...");
		Thread.sleep(this.random.nextInt(1000));
	}

	@Override
	public String toString() {
		return "Philosopher [id=" + id + "]";
	}
}
