package com.orzelowski.complaintservice.service;

import com.orzelowski.complaintservice.Status;
import com.orzelowski.complaintservice.dto.IpApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IPServiceTest {

    @Mock
    WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private IPService ipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCountryBasedOnIP_IpValidAndApiResponseOK_ReturnsCountry() {
        //Arrange
        IpApiResponse apiResponse = new IpApiResponse("Poland", "PL", Status.success);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/{ip}", "1.2.3.4")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(IpApiResponse.class)).thenReturn(Mono.just(apiResponse));

        // Act
        String result = ipService.getCountryBasedOnIP("1.2.3.4");

        Assertions.assertEquals("Poland", result);
    }

    @Test
    public void getCountryBasedOnIP_IpInvalidAndApiResponseOK_ReturnsUnknown() {
        //Arrange
        IpApiResponse apiResponse = new IpApiResponse("", "", Status.fail);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/{ip}", "1.2.3.4")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(IpApiResponse.class)).thenReturn(Mono.just(apiResponse));

        // Act
        String result = ipService.getCountryBasedOnIP("1.2.3.4");

        Assertions.assertEquals("Unknown", result);
    }

    @Test
    public void getCountryBasedOnIP_ResponseFailure_ReturnsUnknown() {
        //Arrange
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/{ip}", "1.2.3.4")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(IpApiResponse.class)).thenReturn(Mono.error(new WebClientResponseException(404, "Not Found", null, null, null)));

        // Act
        String result = ipService.getCountryBasedOnIP("1.2.3.4");

        Assertions.assertEquals("Unknown", result);
    }
}