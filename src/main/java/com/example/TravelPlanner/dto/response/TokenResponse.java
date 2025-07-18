package com.example.TravelPlanner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TokenResponse(
    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkRvZyIsImlhdCI6MTYwOTI2ODQwMCwiZXhwIjoxNjA5MjY4NzAwfQ.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
    String accessToken,

    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYwOTI2ODQwMCwiZXhwIjoxNjA5MzUyODAwfQ.E0yb48TcQdOi_BZ44XfiAxaLf66jJSSQZWJ2_WiUoYk")
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
