package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse getMemberInfo(
            Long memberId
    ) {
        Member member = findMemberOrThrow(memberId);

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public MemberResponse updateMember(
            Long memberId,
            MemberUpdateRequest request
    ) {
        Member member = findMemberOrThrow(memberId);

        member.updateInfo(request);

        return MemberResponse.fromEntity(member);
    }

    @Transactional
    public void deleteMember(
            Long memberId
    ) {
        memberRepository.deleteById(memberId);
    }

    private Member findMemberOrThrow(
            Long memberId
    ) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
