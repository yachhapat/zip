package com.example;

import java.util.List;

/**
 * Simple calculator utility for CI pipeline demo.
 */
public final class Calculator {

    private Calculator() {
    }

    /**
     * Adds two numbers.
     */
    public static long add(long a, long b) {
        return a + b;
    }

    /**
     * Subtracts b from a.
     */
    public static long subtract(long a, long b) {
        return a - b;
    }

    /**
     * Multiplies two numbers.
     */
    public static long multiply(long a, long b) {
        return a * b;
    }

    /**
     * Divides a by b. Throws if b is zero.
     */
    public static double divide(long a, long b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return (double) a / b;
    }
}
