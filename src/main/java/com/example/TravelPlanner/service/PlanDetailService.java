package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.controller.dto.response.PlanDetailResponse;
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
public class PlanDetailService {
    private final PlanDetailRepository planDetailRepository;
    private final PlanRepository planRepository;

    @Transactional
    public PlanDetailResponse createDetail(Long planId, PlanDetailRequest request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        return PlanDetailResponse.fromEntity(
                planDetailRepository.save(
                        request.toEntity(plan)
                )
        );
    }

    @Transactional
    public List<PlanDetailResponse> createDetails(Long planId, List<PlanDetailRequest> request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        List<PlanDetail> planDetails = request.stream()
                .map(planDetail -> planDetail.toEntity(plan))
                .toList();

        planDetailRepository.saveAll(planDetails);

        return planDetails.stream()
                .map(PlanDetailResponse::fromEntity)
                .toList();
    }
}
