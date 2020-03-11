package com.vikas.concurrency.chp13;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue is a priority-ordered queue, which is useful when you
 * want to process elements in an order other than FIFO. Just like other sorted
 * collections, PriorityBlockingQueue can compare elements according to their
 * natural order (if they implement Comparable) or using a Comparator. It is an
 * implementation of unbounded concurrent BlockinQueue. There are no null items
 * allowed.
 */
public class PriorityBlockingQueueExample {

	public static void main(String[] args) {
		PriorityBlockingQueue<Person> queue = new PriorityBlockingQueue<>();
		new Thread(new WorkerDelay(queue)).start();
		new Thread(new Worker1Delay(queue)).start();
	}

}

class WorkerDelay implements Runnable {

	private BlockingQueue<Person> queue;

	public WorkerDelay(BlockingQueue<Person> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			queue.put(new Person("Vikas", new Integer(20)));
			queue.put(new Person("Anil", new Integer(35)));
			Thread.sleep(1000);
			queue.put(new Person("Kartik", new Integer(28)));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Worker1Delay implements Runnable {

	private BlockingQueue<Person> queue;

	public Worker1Delay(BlockingQueue<Person> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			System.out.println(queue.take());
			Thread.sleep(1000);
			System.out.println(queue.take());
			Thread.sleep(1000);
			System.out.println(queue.take());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Person implements Comparable<Person>{
	String name;
	Integer age;

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Person o) {
		return Integer.compare(this.age, o.getAge());
	}

}
