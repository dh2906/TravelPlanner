package com.example.TravelPlanner.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenResponse fromTokens(
            String accessToken,
            String refreshToken
    ) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
