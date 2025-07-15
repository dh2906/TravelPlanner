package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.LoginRequest;
import com.example.TravelPlanner.dto.request.SignupRequest;
import com.example.TravelPlanner.dto.response.MemberResponse;
import com.example.TravelPlanner.dto.response.TokenResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.global.jwt.JwtProvider;
import com.example.TravelPlanner.global.util.PasswordEncoder;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public MemberResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(ExceptionCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = PasswordEncoder.encode(request.password());

        return MemberResponse.fromEntity(
                memberRepository.save(request.toEntity(encodedPassword))
        );
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElse(null);

        if (member == null || !PasswordEncoder.matches(request.password(), member.getPassword()))
            throw new CustomException(ExceptionCode.LOGIN_FAILED);

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        return TokenResponse.fromTokens(accessToken, refreshToken);
    }

    public TokenResponse refresh(
            String refreshToken
    ) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new CustomException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Long memberId = jwtProvider.extractMemberId(refreshToken);

        if (memberId == null) {
            throw new CustomException(ExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.createAccessToken(memberId, member.getRole().name());
        String newRefreshToken = jwtProvider.createRefreshToken(memberId);

        return TokenResponse.fromTokens(newAccessToken, newRefreshToken);
    }
}
