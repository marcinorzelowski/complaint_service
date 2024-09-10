package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.enums.Status;
import com.orzelowski.complaintservice.dto.IpApiResponse;
import com.orzelowski.complaintservice.exception.IPApiException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IPService {

    private final WebClient ipLocationClient;
    private static final String UNKNOWN_COUNTRY = "Unknown";
    public IPService(@Qualifier("IPLocationClient") WebClient webClient) {
        this.ipLocationClient = webClient;
    }

    public String getCountryBasedOnIP(String ip) {
        try {
            IpApiResponse apiResponse = getIpData(ip);
            return apiResponse.status() == Status.success ? apiResponse.country() : UNKNOWN_COUNTRY;
        } catch (IPApiException e) {
            return UNKNOWN_COUNTRY;
        }
    }

    private IpApiResponse getIpData(String ip) throws IPApiException {
        try {
            return ipLocationClient.get()
                    .uri("/{ip}", ip)
                    .retrieve()
                    .bodyToMono(IpApiResponse.class)
                    .block();
        } catch (Exception e) {
            throw new IPApiException("Failed to fetch IP data. Problem with server.");
        }

    }
}
