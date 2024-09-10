package com.orzelowski.complaintservice.dto;

import java.time.LocalDateTime;

public record ComplaintResponse(
        Long id,
        String text,
        int requestCounter,
        Long authorId,
        Long productId,
        LocalDateTime creationDate
) {
}
