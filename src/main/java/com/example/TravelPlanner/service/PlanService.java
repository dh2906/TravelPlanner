package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.PlanRequest;
import com.example.TravelPlanner.controller.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<PlanResponse> getPlansByMemberId(Long memberId) {
        List<Plan> plans = planRepository.findAllByMemberId(memberId);

        return plans.stream()
                .map(PlanResponse::fromEntity)
                .toList();
    }
}
