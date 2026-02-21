package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

class DiscountEngineTest {

    @Test
    void summer20On100ReturnsEighty() {
        assertEquals(70.0, DiscountEngine.applyDiscount(100.0, "SUMMER20", "GUEST"));
    }

    @Test
    void welcome10On100ReturnsNinety() {
        assertEquals(90.0, DiscountEngine.applyDiscount(100.0, "WELCOME10", "GUEST"));
    }

    @Test
    void proUserGetsExtraFiveDollarsOff() {
        assertEquals(75.0, DiscountEngine.applyDiscount(100.0, "SUMMER20", "PRO"));
    }

    @Test
    void fiveDollarProDiscountClampsToZero() {
        assertEquals(0.0, DiscountEngine.applyDiscount(5.0, null, "PRO"));
    }

    @Test
    void invalidDiscountCodeThrows() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount(100.0, "BOGUS", "GUEST"));
        assertEquals("Invalid discount code: BOGUS", thrown.getMessage());
    }
}
