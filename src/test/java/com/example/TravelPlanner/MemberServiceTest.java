package com.example.TravelPlanner;

import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 회원정보_조회_성공() {
        Long memberId = 1L;

        Member member = Member.builder()
            .id(memberId)
            .email("test@example.com")
            .name("tester")
            .build();

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        MemberResponse response = memberService.getMemberInfo(memberId);

        assertThat(response.id()).isEqualTo(memberId);
        assertThat(response.email()).isEqualTo("test@example.com");
        assertThat(response.name()).isEqualTo("tester");
    }

    @Test
    void 회원정보_조회_실패_멤버없음() {
        Long memberId = 1L;

        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getMemberInfo(memberId))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void 회원정보_수정_성공() {
        Long memberId = 1L;
        MemberUpdateRequest request = new MemberUpdateRequest("password123", "update name");

        Member member = Member.builder()
            .id(memberId)
            .name("tester")
            .build();

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        MemberResponse response = memberService.updateMember(memberId, request);

        assertThat(response.name()).isEqualTo("update name");
    }

    @Test
    void 회원정보_수정_실패_멤버없음() {
        Long memberId = 1L;
        MemberUpdateRequest request = new MemberUpdateRequest("password123", "update name");

        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.updateMember(memberId, request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void 회원삭제_성공() {
        Long memberId = 1L;

        memberService.deleteMember(memberId);

        verify(memberRepository).deleteById(memberId);
    }
}
