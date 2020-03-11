package com.vikas.geeksforgeeks.messagePassing;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class SharedResource {
	static Queue<Integer> queue = new LinkedBlockingDeque<>();
	static volatile int size = 0;
}
