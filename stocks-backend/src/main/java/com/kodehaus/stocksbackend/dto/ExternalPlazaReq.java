package com.kodehaus.stocksbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalPlazaReq(
    @JsonProperty("externalId") String externalId,
    String name,
    String description,
    String address,
    @JsonProperty("phoneNumber") String phoneNumber,
    String email,
    @JsonProperty("openingHours") String openingHours,
    @JsonProperty("closingHours") String closingHours
) {}
