package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.LoginRequest;
import com.example.TravelPlanner.controller.dto.request.SignupRequest;
import com.example.TravelPlanner.controller.dto.response.LoginResponse;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.service.AuthService;
import jakarta.servlet.http.Cookie;
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
    public void login(
            @RequestBody @Valid LoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        LoginResponse response = authService.login(request);

        Cookie accessTokenCookie = new Cookie("accessToken", response.accessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(30 * 60);

        Cookie refreshTokenCookie = new Cookie("refreshToken", response.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60);

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);
    }
}
