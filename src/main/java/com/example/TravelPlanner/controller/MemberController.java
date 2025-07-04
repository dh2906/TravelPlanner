package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
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
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMyInfo(
            @LoginMember Member member
    ) {
        return ResponseEntity.ok(
                MemberResponse.fromEntity(member)
        );
    }

    @PutMapping("/me")
    public ResponseEntity<MemberResponse> updateMyInfo(
            @LoginMember Member member,
            @RequestBody @Valid MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.updateMember(member, request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(
            @LoginMember Member member,
            HttpServletResponse httpServletResponse
    ) {
        memberService.deleteMember(member);

        Cookie accessTokenCookie = TokenCookieUtil.clearAccessToken();
        Cookie refreshTokenCookie = TokenCookieUtil.clearRefreshToken();

        httpServletResponse.addCookie(accessTokenCookie);
        httpServletResponse.addCookie(refreshTokenCookie);

        return ResponseEntity
                .noContent()
                .build();
    }
}
