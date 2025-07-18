package com.example.TravelPlanner.controller.api;

import com.example.TravelPlanner.dto.request.LoginRequest;
import com.example.TravelPlanner.dto.request.SignupRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "인증 API", description = "회원가입, 로그인, 로그아웃, 토큰 재발급")
public interface AuthApi {
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "회원 정보를 입력받아 가입한다.")
    ResponseEntity<MemberResponse> signup(SignupRequest request);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "이메일과 비밀번호로 로그인한다.")
    ResponseEntity<TokenResponse> login(
        LoginRequest request,
        HttpServletResponse response
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "리프레시 토큰으로 새로운 토큰을 발급한다.")
    ResponseEntity<TokenResponse> refresh(
        HttpServletRequest request,
        HttpServletResponse response
    );
}
