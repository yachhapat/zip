package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void addTwoPositiveNumbers() {
        assertEquals(5, Calculator.add(2, 3));
    }

    @Test
    void addNegativeNumbers() {
        assertEquals(-2, Calculator.add(-1, -1));
    }

    @Test
    void addZero() {
        assertEquals(5, Calculator.add(5, 0));
    }

    @Test
    void subtractTwoNumbers() {
        assertEquals(2, Calculator.subtract(5, 3));
    }

    @Test
    void subtractHandlesNegativeResult() {
        assertEquals(-4, Calculator.subtract(1, 5));
    }

    @Test
    void multiplyTwoNumbers() {
        assertEquals(12, Calculator.multiply(4, 3));
    }

    @Test
    void multiplyByZero() {
        assertEquals(0, Calculator.multiply(5, 0));
    }

    @Test
    void divideTwoNumbers() {
        assertEquals(5.0, Calculator.divide(10, 2));
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.divide(1, 0));
    }
}
