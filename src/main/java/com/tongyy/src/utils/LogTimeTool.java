package com.tongyy.src.utils;

import java.util.stream.Stream;


public class LogTimeTool {

    public static void logTime(LogTime func) {
        long startTime = System.nanoTime();
        func.call();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.print(duration);
        double seconds = (double) duration / 1000000000.0;
        System.out.println("  secondes:" + seconds);
    }
}
