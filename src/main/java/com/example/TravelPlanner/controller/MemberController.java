package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
