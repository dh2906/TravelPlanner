package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.api.MemberApi;
import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.global.util.TokenCookieUtil;
import com.example.TravelPlanner.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberApi {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(
        @LoginMember Long memberId
    ) {
        MemberResponse response = memberService.getMemberInfo(memberId);
        return ResponseEntity
            .ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<MemberResponse> updateMyInfo(
        @LoginMember Long memberId,
        @RequestBody @Valid MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.updateMember(memberId, request);

        return ResponseEntity
            .ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
        @LoginMember Long memberId,
        HttpServletResponse httpServletResponse
    ) {
        memberService.deleteMember(memberId);

        Cookie accessTokenCookie = TokenCookieUtil.clearAccessToken();
        Cookie refreshTokenCookie = TokenCookieUtil.clearRefreshToken();

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return ResponseEntity
            .noContent()
            .build();
    }
}
