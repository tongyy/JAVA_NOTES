package com.tongyy.java8;

import com.tongyy.src.utils.LogTime;
import com.tongyy.src.utils.LogTimeTool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamPerformanceDemo extends LogTimeTool{

	public static void main(String[] args) {
		List<Integer> myList = new ArrayList<>();
		for (int i = 0; i < 100; i++)
			myList.add(i);

		// sequential stream
		Stream<Integer> sequentialStream = myList.stream();

		// parallel stream
		Stream<Integer> parallelStream = myList.parallelStream();

		logTime(()->testStream(sequentialStream));

		logTime(()->testStream(parallelStream));


	}
	private static void testStream(Stream<Integer> s) {
		s.forEach(c -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
