package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.PlanRequest;
import com.example.TravelPlanner.controller.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    @Transactional
    public PlanResponse createPlan(Member member, PlanRequest request) {
        return PlanResponse.fromEntity(
                planRepository.save(
                        request.toEntity(member)
                )
        );
    }
}
