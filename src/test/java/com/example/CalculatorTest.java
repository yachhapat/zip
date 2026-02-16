package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void calculatorInit() {
        assertEquals(0, Calculator.add(0, 0));
        assertEquals(0, Calculator.subtract(0, 0));
        assertEquals(0, Calculator.multiply(0, 0));
        assertEquals(1.0, Calculator.divide(1, 1));
    }

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
    void addPositiveAndNegative() {
        assertEquals(2, Calculator.add(5, -3));
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
    void subtractZero() {
        assertEquals(5, Calculator.subtract(5, 0));
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
    void multiplyNegativeNumbers() {
        assertEquals(-6, Calculator.multiply(-2, 3));
    }

    @Test
    void multiplyTwoNegatives() {
        assertEquals(6, Calculator.multiply(-2, -3));
    }

    @Test
    void divideTwoNumbers() {
        assertEquals(5.0, Calculator.divide(10, 2));
    }

    @Test
    void divideProducesDecimalResult() {
        assertEquals(3.5, Calculator.divide(7, 2), 0.0001);
    }

    @Test
    void divideByOne() {
        assertEquals(9.0, Calculator.divide(9, 1));
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.divide(1, 0));
    }

    @Test
    void divideByZeroThrowsWithCorrectMessage() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> Calculator.divide(1, 0));
        assertEquals("Division by zero", thrown.getMessage());
    }
}
