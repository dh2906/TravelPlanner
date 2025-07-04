package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.LoginRequest;
import com.example.TravelPlanner.controller.dto.request.SignupRequest;
import com.example.TravelPlanner.controller.dto.response.LoginResponse;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.entity.Member;
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
        String encodedPassword = PasswordEncoder.encode(request.password());

        return MemberResponse.fromEntity(
                memberRepository.save(request.toEntity(encodedPassword))
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        if (!PasswordEncoder.matches(request.password(), member.getPassword()))
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken();

        return LoginResponse.fromTokens(accessToken, refreshToken);
    }
}
