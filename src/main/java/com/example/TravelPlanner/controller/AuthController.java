package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.SignupRequest;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    public ResponseEntity<MemberResponse> signup(SignupRequest request) {
        MemberResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
