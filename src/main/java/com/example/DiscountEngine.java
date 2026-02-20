package com.example;

import java.util.Map;

/**
 * Discount Logic Engine.
 *
 * Applies percentage-based discount codes and flat user-type discounts.
 * Guarantees the final price is never negative.
 */
public final class DiscountEngine {

    private DiscountEngine() {
    }

    /**
     * Calculates the discounted price.
     *
     * @param originalPrice the original price (must be >= 0)
     * @param discountCode  one of "WELCOME10", "SUMMER20", or null/empty for none
     * @param userType      one of "PRO" or "GUEST"
     * @return final price after all discounts, never below 0
     */
    public static double applyDiscount(double originalPrice, String discountCode, String userType) {
        if (originalPrice < 0) {
            throw new IllegalArgumentException("Original price cannot be negative");
        }
        if (userType == null || (!userType.equals("PRO") && !userType.equals("GUEST"))) {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }

        double price = originalPrice;

        if ("WELCOME10".equals(discountCode)) {
            price = price * 0.90;
        } else if ("SUMMER20".equals(discountCode)) {
            price = price * 0.80;
        } else if (discountCode != null && !discountCode.isEmpty()) {
            throw new IllegalArgumentException("Invalid discount code: " + discountCode);
        }

        if ("PRO".equals(userType)) {
            price = price - 5.0;
        }

        return Math.max(price, 0.0);
    }

    /**
     * Convenience overload that accepts the price as a String.
     * Throws a clear error if the value is not a valid number.
     */
    public static double applyDiscount(String originalPriceStr, String discountCode, String userType) {
        if (originalPriceStr == null) {
            throw new IllegalArgumentException("Original price must not be null");
        }
        try {
            double originalPrice = Double.parseDouble(originalPriceStr);
            return applyDiscount(originalPrice, discountCode, userType);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Original price must be a valid number, got: " + originalPriceStr);
        }
    }
}
