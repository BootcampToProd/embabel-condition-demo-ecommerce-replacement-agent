package com.bootcamptoprod.dto;

public record ReplacementRequest(
        String productSku,
        String problemDescription
) {
}