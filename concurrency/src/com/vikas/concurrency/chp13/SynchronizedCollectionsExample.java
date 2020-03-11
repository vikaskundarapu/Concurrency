package com.vikas.concurrency.chp13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SynchronizedCollectionsExample {

	public static void main(String[] args) {
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		new Thread(new Worker11(map)).start();
		new Thread(new Worker12(map)).start();
		
		List<Integer> list = new ArrayList<>();
		List<Integer> synchronizedList = Collections.synchronizedList(list);
		Set<Integer>integers = new HashSet<>();
		Set<Integer> synchronizedSet = Collections.synchronizedSet(integers);
		Map<String, String> map2 = new HashMap<>();
		Map<String, String> synchronizedMap = Collections.synchronizedMap(map2);
		
	}
}

class Worker11 implements Runnable {
	ConcurrentMap<String, Integer> map;
	public Worker11(ConcurrentMap<String, Integer> map) {
		super();
		this.map = map;
	}

	@Override
	public void run() {
		try {
			map.put("A", 1);
			map.put("B", 2);
			Thread.sleep(1000);
			map.put("C", 3);
			map.put("D", 4);
			Thread.sleep(1000);
			map.put("E", 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Worker12 implements Runnable {
	ConcurrentMap<String, Integer> map;
	public Worker12(ConcurrentMap<String, Integer> map) {
		super();
		this.map = map;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			System.out.println(map.get("A"));
			Thread.sleep(1000);
			System.out.println(map.get("E"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}