package com.bootcamptoprod.dto;

public record FinalDecision(
        String decisionCode,   // "REPLACEMENT_ISSUED" or "REFUND_ISSUED"
        String message
) {
}