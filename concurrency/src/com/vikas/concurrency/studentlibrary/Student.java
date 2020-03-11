package com.vikas.concurrency.studentlibrary;

import java.util.Random;

public class Student implements Runnable {

	private int id;
	private Book[] books;

	public Student(int id, Book[] books) {
		this.id = id;
		this.books = books;
	}

	@Override
	public String toString() {
		return "Student " + id;
	}

	@Override
	public void run() {
		Random random = new Random();
		try {
			while (true) {
				int randomBook = random.nextInt(Constants.NUMBER_OF_BOOKS);
				books[randomBook].read(this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
