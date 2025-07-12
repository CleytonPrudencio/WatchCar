package com.system.watchCar.dto.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TokenResponseDTO(String access_token, Instant expire, String refreshToken) {
}
