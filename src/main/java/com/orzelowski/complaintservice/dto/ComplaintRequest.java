package com.orzelowski.complaintservice.dto;

public record ComplaintRequest(
    String text,
    Long authorId,
    Long productId
) {
}
