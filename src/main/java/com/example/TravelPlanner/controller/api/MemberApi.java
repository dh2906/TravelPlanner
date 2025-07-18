package com.example.TravelPlanner.controller.api;

import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.global.annotation.resolver.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/members")
@Tag(name = "회원 API", description = "회원 정보 조회, 수정 및 탈퇴 API")
public interface MemberApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "현재 로그인한 회원의 정보를 조회한다.")
    ResponseEntity<MemberResponse> getMyInfo(Long memberId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "현재 로그인한 회원의 정보를 수정한다.")
    ResponseEntity<MemberResponse> updateMyInfo(
        Long memberId,
        MemberUpdateRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "현재 로그인한 회원의 계정을 삭제한다.")
    ResponseEntity<Void> deleteMyAccount(
        @LoginMember Long memberId,
        HttpServletResponse httpServletResponse
    );
}

