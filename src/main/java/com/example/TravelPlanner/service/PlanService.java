package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.PlanRequest;
import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.dto.response.PlanWithDetailsResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.repository.PlanDetailRepository;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanDetailRepository planDetailRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PlanResponse createPlan(Long memberId, PlanRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

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

        List<PlanDetail> planDetails = planDetailRepository.findAllByPlanIdOrderByDayNumberAscStartTimeAsc(planId);

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

    @Transactional
    public String getPlanSharePath(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        if (!plan.getVisibility().equals(Plan.Visibility.PRIVATE)) {
            throw new CustomException(ExceptionCode.SHARING_NOT_ALLOWED);
        }

        if (plan.getSharePath() != null) {
            return plan.getSharePath();
        }

        String sharePath = UUID.randomUUID().toString();
        plan.assignShareUrl(sharePath);

        return sharePath;
    }
}
