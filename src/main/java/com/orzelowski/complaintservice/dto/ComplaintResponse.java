package com.orzelowski.complaintservice.dto;

import java.time.LocalDateTime;

public record ComplaintResponse(
        Long id,
        String text,
        int requestCounter,
        String country,
        Long authorId,
        Long productId,
        LocalDateTime creationDate
) {
}
