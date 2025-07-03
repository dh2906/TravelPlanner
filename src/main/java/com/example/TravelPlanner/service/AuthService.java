package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.SignupRequest;
import com.example.TravelPlanner.controller.dto.response.MemberResponse;
import com.example.TravelPlanner.global.util.PasswordEncoder;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public MemberResponse signup(SignupRequest request) {
        String encodedPassword = PasswordEncoder.encode(request.password());

        return MemberResponse.fromEntity(
                memberRepository.save(request.toEntity(encodedPassword))
        );
    }
}
