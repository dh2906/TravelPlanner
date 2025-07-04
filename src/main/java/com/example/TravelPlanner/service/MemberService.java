package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.jwt.JwtProvider;
import com.example.TravelPlanner.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse updateMember(Member member, MemberUpdateRequest request) {
        member.updateInfo(request);

        return MemberResponse.fromEntity(member);
    }
}
