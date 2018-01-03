package com.tongyy.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamPerformanceDemo {

	public static void main(String[] args) {
		List<Integer> myList = new ArrayList<>();
		for(int i=0; i<100; i++) 
			myList.add(i);
		
		//sequential stream
		Stream<Integer> sequentialStream = myList.stream();
		
		//parallel stream
		Stream<Integer> parallelStream = myList.parallelStream();
		
		//using lambda with Stream API, filter example
		Stream<Integer> highNums = parallelStream.filter(p -> p > 90);
		logTime(highNums);
		Stream<Integer> highNumsSeq = sequentialStream.filter(p -> p > 90);
		logTime(highNumsSeq);

	}
	
	private static void logTime(Stream<Integer> s) {
		long startTime = System.nanoTime();
		s.forEach(p -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		System.out.print(duration);
		double seconds = (double)duration / 1000000000.0;
		System.out.println("  secondes:"+seconds);
	}
}
