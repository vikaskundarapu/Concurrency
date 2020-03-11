package com.vikas.concurrency.chp13;

import java.util.concurrent.Exchanger;

/**
 * Another form of barrier is Exchanger, a two-party barrier in which the
 * parties exchange data at the barrier point. Exchangers are useful when the
 * parties perform asymmetric activities, for example when one thread fills a
 * buffer with data and the other thread consumes the data from the buffer;
 * these threads could use an Exchanger to meet and exchange a full buffer for
 * an empty one. When two threads exchange objects via an Exchanger, the
 * exchange constitutes a safe publication of both objects to the other party.
 * The timing of the exchange depends on the responsiveness requirements of the
 * application. The simplest approach is that the filling task exchanges when
 * the buffer is full, and the emptying task exchanges when the buffer is empty;
 * this minimizes the number of exchanges but can delay processing of some data
 * if the arrival rate of new data is unpredictable. Another approach would be
 * that the filler exchanges when the buffer is full, but also when the buffer
 * is partially filled and a certain amount of time has elapsed.
 */
public class ExchangerExample {
	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		new Thread(new FirstThread(exchanger)).start();
		new Thread(new SecondThread(exchanger)).start();

	}
}

class FirstThread implements Runnable {

	int counter;
	Exchanger<Integer> exchanger;

	public FirstThread(Exchanger<Integer> exchange) {
		super();
		this.exchanger = exchange;
	}

	@Override
	public void run() {

		while (true) {
			try {
				counter = counter + 1;
				System.out.println("FirstTHread has incremented the counter : " + counter);
				counter =exchanger.exchange(counter);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

class SecondThread implements Runnable {

	int counter;
	Exchanger<Integer> exchanger;

	public SecondThread(Exchanger<Integer> exchange) {
		super();
		this.exchanger = exchange;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(1000);
				counter = counter - 1;
				System.out.println("SecondTHread has decremented the counter : " + counter);
				counter = exchanger.exchange(counter);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
