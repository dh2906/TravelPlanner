package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.PlanRequest;
import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.dto.response.PlanWithDetailsResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.PlanDetailRepository;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanDetailRepository planDetailRepository;

    @Transactional
    public PlanResponse createPlan(Member member, PlanRequest request) {
        return PlanResponse.fromEntity(
                planRepository.save(
                        request.toEntity(member)
                )
        );
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getPlansByMemberId(Long memberId) {
        List<Plan> plans = planRepository.findAllByMemberId(memberId);

        return plans.stream()
                .map(PlanResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getPublicPlans() {
        List<Plan> plans = planRepository.findAllByVisibility(Plan.Visibility.PUBLIC);

        return plans.stream()
                .map(PlanResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlanWithDetailsResponse getPlanWithDetailByPlanId(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        List<PlanDetail> planDetails = planDetailRepository.findAllByPlanId(planId);

        return PlanWithDetailsResponse.fromEntities(plan, planDetails);
    }

    @Transactional
    public PlanResponse updatePlan(Long planId, PlanRequest request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        plan.updateInfo(request);

        return PlanResponse.fromEntity(plan);
    }

    @Transactional
    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }
}
