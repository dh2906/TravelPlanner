package com.example.TravelPlanner.global.filter;

import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.global.jwt.JwtProvider;
import com.example.TravelPlanner.repository.MemberRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String token = extractTokenFromCookie(request);

            if (token != null && jwtProvider.validateToken(token)) {
                Long memberId = jwtProvider.extractMemberId(token);
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

                // request 속성에 인증된 멤버 저장
                request.setAttribute("loginMember", member);
            }

            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
        }
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
