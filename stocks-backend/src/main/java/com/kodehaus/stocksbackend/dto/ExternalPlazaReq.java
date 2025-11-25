package com.kodehaus.stocksbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalPlazaReq(
    @JsonProperty("external_id") String externalId,
    String name,
    String email,
    String address,
    @JsonProperty("created_at") String createdAt,
    String description,
    @JsonProperty("is_active") String isActive,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("opening_hours") String openingHours,
    @JsonProperty("closing_hours") String closingHours
) {}
