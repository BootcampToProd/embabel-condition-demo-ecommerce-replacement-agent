package com.bootcamptoprod.dto;

public record InventoryStatus(
        String productSku,
        boolean inStock
) {
}