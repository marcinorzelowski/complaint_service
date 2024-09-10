package com.orzelowski.complaintservice.dto;

import com.orzelowski.complaintservice.enums.Status;

public record IpApiResponse(String country, String countryCode, Status status) {
}
