package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.dto.request.LoginRequest;
import com.example.TravelPlanner.dto.request.SignupRequest;
import com.example.TravelPlanner.dto.response.TokenResponse;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.global.util.TokenCookieUtil;
import com.example.TravelPlanner.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(
            @RequestBody @Valid SignupRequest request
    ) {
        MemberResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        TokenResponse response = authService.login(request);

        Cookie accessTokenCookie = TokenCookieUtil.createAccessToken(response.accessToken());
        Cookie refreshTokenCookie = TokenCookieUtil.createRefreshToken(response.refreshToken());

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return ResponseEntity
                .ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie accessTokenCookie = TokenCookieUtil.clearAccessToken();
        Cookie refreshTokenCookie = TokenCookieUtil.clearRefreshToken();

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        String refreshToken = TokenCookieUtil.getRefreshTokenFromRequest(httpServletRequest);

        TokenResponse response = authService.refresh(refreshToken);

        Cookie accessTokenCookie = TokenCookieUtil.createAccessToken(response.accessToken());
        Cookie refreshTokenCookie = TokenCookieUtil.createRefreshToken(response.refreshToken());

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return ResponseEntity
                .ok(response);
    }
}
