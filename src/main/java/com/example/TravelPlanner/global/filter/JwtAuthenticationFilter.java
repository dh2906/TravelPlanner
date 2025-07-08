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
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/signup",
            "/api/auth/login",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/swagger-resources"
    );

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();

        if (EXCLUDED_PATHS.stream().noneMatch(path::equals)) {
            try {
                String token = extractTokenFromCookie(request);

                if (token != null && jwtProvider.validateToken(token)) {
                    Long memberId = jwtProvider.extractMemberId(token);
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
                    request.setAttribute("loginMember", member);
                }

            } catch (CustomException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
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

    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
