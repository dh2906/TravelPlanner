package com.example.TravelPlanner.global.config;

import com.example.TravelPlanner.global.interceptor.PlanAccessInterceptor;
import com.example.TravelPlanner.global.resolver.LoginMemberArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final PlanAccessInterceptor planAccessInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(planAccessInterceptor)
                .addPathPatterns("/api/plans/**");
    }
}
