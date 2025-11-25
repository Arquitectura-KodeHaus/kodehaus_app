package com.kodehaus.stocksbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalPlazaReq(
    @JsonProperty("externalId") String externalId,
    String name,
    String email,
    String address,
    @JsonProperty("createdAt") String createdAt,
    String description,
    @JsonProperty("isActive") String isActive,
    @JsonProperty("phoneNumber") String phoneNumber,
    @JsonProperty("openingHours") String openingHours,
    @JsonProperty("closingHours") String closingHours
) {}
