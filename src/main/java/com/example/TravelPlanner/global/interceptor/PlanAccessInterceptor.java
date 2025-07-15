package com.example.TravelPlanner.global.interceptor;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.PlanRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class PlanAccessInterceptor implements HandlerInterceptor {
    private final PlanRepository planRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long loginMemberId = (Long) request.getAttribute("loginMemberId");

        if (loginMemberId == null) {
            throw new CustomException(ExceptionCode.UNAUTHORIZED);
        }

        String method = request.getMethod();
        String path = request.getRequestURI();
        Long planId = extractPlanIdFromPath(path);

        if (planId == null) {
            return true;
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        if (!plan.getMember().getId().equals(loginMemberId)) {
            if (!"GET".equalsIgnoreCase(method)) {
                throw new CustomException(ExceptionCode.ACCESS_DENIED);
            } else if (plan.getVisibility() != Plan.Visibility.PUBLIC) {
                throw new CustomException(ExceptionCode.ACCESS_DENIED);
            }
        }

        return true;
    }

    private Long extractPlanIdFromPath(String path) {
        try {
            String[] parts = path.split("/");

            for (int i = 0; i < parts.length; i++) {
                if ("plans".equals(parts[i]) && i + 1 < parts.length) {
                    return Long.valueOf(parts[i + 1]);
                }
            }
        } catch (NumberFormatException ignored) {

        }

        return null;
    }
}
