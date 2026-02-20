package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DiscountEngineTest {

    // ---------------------------------------------------------------
    // Happy Path
    // ---------------------------------------------------------------

    @Test
    void summer20On100ReturnsEighty() {
        assertEquals(80.0, DiscountEngine.applyDiscount(100.0, "SUMMER20", "GUEST"));
    }

    @Test
    void welcome10On100ReturnsNinety() {
        assertEquals(90.0, DiscountEngine.applyDiscount(100.0, "WELCOME10", "GUEST"));
    }

    @Test
    void noDiscountCodeReturnsOriginalPrice() {
        assertEquals(100.0, DiscountEngine.applyDiscount(100.0, null, "GUEST"));
    }

    @Test
    void emptyDiscountCodeReturnsOriginalPrice() {
        assertEquals(100.0, DiscountEngine.applyDiscount(100.0, "", "GUEST"));
    }

    // ---------------------------------------------------------------
    // PRO user flat discount
    // ---------------------------------------------------------------

    @Test
    void proUserGetsExtraFiveDollarsOff() {
        // $100 - 20% = $80, then -$5 = $75
        assertEquals(75.0, DiscountEngine.applyDiscount(100.0, "SUMMER20", "PRO"));
    }

    @Test
    void proUserWithWelcome10() {
        // $100 - 10% = $90, then -$5 = $85
        assertEquals(85.0, DiscountEngine.applyDiscount(100.0, "WELCOME10", "PRO"));
    }

    @Test
    void proUserNoDiscountCode() {
        // $100 - $5 = $95
        assertEquals(95.0, DiscountEngine.applyDiscount(100.0, null, "PRO"));
    }

    // ---------------------------------------------------------------
    // Boundary Values — price never goes below $0
    // ---------------------------------------------------------------

    @Test
    void fiveDollarProDiscountClampsToZero() {
        // $5 - $5 PRO discount = $0, not -$5
        assertEquals(0.0, DiscountEngine.applyDiscount(5.0, null, "PRO"));
    }

    @Test
    void threeDollarProDiscountClampsToZero() {
        // $3 - $5 PRO discount would be -$2, clamped to $0
        assertEquals(0.0, DiscountEngine.applyDiscount(3.0, null, "PRO"));
    }

    @Test
    void zeroPriceStaysZero() {
        assertEquals(0.0, DiscountEngine.applyDiscount(0.0, "SUMMER20", "GUEST"));
    }

    @Test
    void zeroPriceProStaysZero() {
        // $0 - $5 PRO discount would be -$5, clamped to $0
        assertEquals(0.0, DiscountEngine.applyDiscount(0.0, null, "PRO"));
    }

    @Test
    void smallPriceWithSummer20ProClampsToZero() {
        // $4 * 0.80 = $3.20, then -$5 = -$1.80, clamped to $0
        assertEquals(0.0, DiscountEngine.applyDiscount(4.0, "SUMMER20", "PRO"));
    }

    // ---------------------------------------------------------------
    // Invalid Input — clear errors
    // ---------------------------------------------------------------

    @Test
    void stringInsteadOfNumberThrowsClearError() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount("abc", "SUMMER20", "GUEST"));
        assertEquals("Original price must be a valid number, got: abc", thrown.getMessage());
    }

    @Test
    void nullPriceStringThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount((String) null, "SUMMER20", "GUEST"));
    }

    @Test
    void negativePriceThrows() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount(-10.0, "SUMMER20", "GUEST"));
        assertEquals("Original price cannot be negative", thrown.getMessage());
    }

    @Test
    void invalidDiscountCodeThrows() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount(100.0, "BOGUS", "GUEST"));
        assertEquals("Invalid discount code: BOGUS", thrown.getMessage());
    }

    @Test
    void invalidUserTypeThrows() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount(100.0, "SUMMER20", "ADMIN"));
        assertEquals("Invalid user type: ADMIN", thrown.getMessage());
    }

    @Test
    void nullUserTypeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> DiscountEngine.applyDiscount(100.0, "SUMMER20", null));
    }
}
