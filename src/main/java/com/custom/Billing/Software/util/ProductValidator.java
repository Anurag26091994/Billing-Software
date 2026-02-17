package com.custom.Billing.Software.util;

import com.custom.Billing.Software.dto.ProductRequestDTO;

import java.time.LocalDateTime;

public class ProductValidator {

    public static void validate(ProductRequestDTO product) {

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (product.getBuyPrice() <= 0) {
            throw new IllegalArgumentException("Buy price must be greater than 0");
        }

        if (product.getSellingPrice() <= 0) {
            throw new IllegalArgumentException("Selling price must be greater than 0");
        }

        if (product.getExpiryDate() != null &&
                product.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in past");
        }
    }
}

