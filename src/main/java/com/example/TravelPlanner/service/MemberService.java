package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse updateMember(Member member, MemberUpdateRequest request) {
        member.updateInfo(request);

        memberRepository.save(member);

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
