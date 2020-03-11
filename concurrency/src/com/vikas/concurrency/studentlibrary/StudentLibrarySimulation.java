package com.vikas.concurrency.studentlibrary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentLibrarySimulation {

	public static void main(String[] args) {

		Student[] students = null;
		Book[] books = null;
		ExecutorService service = null;

		try {
			service = Executors.newFixedThreadPool(Constants.NUMBER_OF_STUDENTS);
			students = new Student[Constants.NUMBER_OF_STUDENTS];
			books = new Book[Constants.NUMBER_OF_BOOKS];

			for (int i = 0; i < books.length; i++) {
				books[i] = new Book(i);
			}

			for (int i = 0; i < students.length; i++) {
				students[i] = new Student(i, books);
				service.execute(students[i]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			service.shutdown();
		} finally {
			service.shutdown();
		}
	}

}
