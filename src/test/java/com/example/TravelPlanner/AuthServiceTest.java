package com.example.TravelPlanner;

import com.example.TravelPlanner.dto.request.LoginRequest;
import com.example.TravelPlanner.dto.request.SignupRequest;
import com.example.TravelPlanner.dto.response.TokenResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.global.jwt.JwtProvider;
import com.example.TravelPlanner.global.util.PasswordEncoder;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void 회원가입_성공() {
        SignupRequest request = new SignupRequest("test@example.com", "password123", "nickname");

        given(memberRepository.existsByEmail(request.email())).willReturn(false);

        Member savedMember = Member.builder()
            .id(1L)
            .email(request.email())
            .password(PasswordEncoder.encode(request.password()))
            .name(request.name())
            .build();

        given(memberRepository.save(any(Member.class))).willReturn(savedMember);

        var response = authService.signup(request);

        assertThat(request.email()).isEqualTo(response.email());
        assertThat(request.name()).isEqualTo(response.name());
    }

    @Test
    void 회원가입_중복_이메일_예외_발생() {
        SignupRequest request = new SignupRequest("test@example.com", "password123", "nickname");
        given(memberRepository.existsByEmail(request.email())).willReturn(true);

        assertThatThrownBy(() -> authService.signup(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.DUPLICATE_EMAIL.getMessage());
    }

    @Test
    void 로그인_성공() {
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = PasswordEncoder.encode(rawPassword);

        LoginRequest request = new LoginRequest(email, rawPassword);

        Member member = Member.builder()
            .id(1L)
            .email(email)
            .password(encodedPassword)
            .role(Member.Role.USER)
            .build();

        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(jwtProvider.createAccessToken(member.getId(), member.getRole().name())).willReturn("access-token");
        given(jwtProvider.createRefreshToken(member.getId())).willReturn("refresh-token");

        TokenResponse tokenResponse = authService.login(request);

        assertThat(tokenResponse.accessToken()).isEqualTo("access-token");
        assertThat(tokenResponse.refreshToken()).isEqualTo("refresh-token");
    }

    @Test
    void 로그인_실패_회원_없음() {
        String email = "unknown@example.com";
        LoginRequest request = new LoginRequest(email, "anyPassword");

        given(memberRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.LOGIN_FAILED.getMessage());
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        String email = "test@example.com";
        String rawPassword = "wrongPassword";

        Member member = Member.builder()
            .id(1L)
            .email(email)
            .password(PasswordEncoder.encode("correctPassword"))
            .role(Member.Role.USER)
            .build();

        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        LoginRequest request = new LoginRequest(email, rawPassword);

        assertThatThrownBy(() -> authService.login(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.LOGIN_FAILED.getMessage());
    }

    @Test
    void 리프레시_성공() {
        // given
        String refreshToken = "valid-refresh-token";
        Long memberId = 1L;
        Member member = Member.builder()
            .id(memberId)
            .role(Member.Role.USER)
            .build();

        given(jwtProvider.validateToken(refreshToken)).willReturn(true);
        given(jwtProvider.extractMemberId(refreshToken)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(jwtProvider.createAccessToken(memberId, member.getRole().name())).willReturn("new-access-token");
        given(jwtProvider.createRefreshToken(memberId)).willReturn("new-refresh-token");

        TokenResponse tokenResponse = authService.refresh(refreshToken);

        assertThat(tokenResponse.accessToken()).isEqualTo("new-access-token");
        assertThat(tokenResponse.refreshToken()).isEqualTo("new-refresh-token");
    }

    @Test
    void 리프레시_실패_토큰_검증_실패() {
        String refreshToken = "invalid-refresh-token";

        given(jwtProvider.validateToken(refreshToken)).willReturn(false);

        assertThatThrownBy(() -> authService.refresh(refreshToken))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.INVALID_REFRESH_TOKEN.getMessage());
    }

    @Test
    void 리프레시_실패_토큰에_멤버_id_없음() {
        String refreshToken = "valid-refresh-token";

        given(jwtProvider.validateToken(refreshToken)).willReturn(true);
        given(jwtProvider.extractMemberId(refreshToken)).willReturn(null);

        assertThatThrownBy(() -> authService.refresh(refreshToken))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.INVALID_REFRESH_TOKEN.getMessage());
    }

    @Test
    void 리프레시_실패_멤버_없음() {
        String refreshToken = "valid-refresh-token";
        Long memberId = 1L;

        given(jwtProvider.validateToken(refreshToken)).willReturn(true);
        given(jwtProvider.extractMemberId(refreshToken)).willReturn(memberId);
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.refresh(refreshToken))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }
}
