package com.github.tomtzook.util;

public class Timer {

    public static double currentTimeSeconds() {
        return System.nanoTime() / 1e9;
    }

    public static void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
