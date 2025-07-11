package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.dto.request.PlanDetailBulkUpdateRequest;
import com.example.TravelPlanner.dto.response.PlanDetailResponse;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.PlanDetailRepository;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        if (planDetailRepository.existsByPlanIdAndDayNumberAndConflictTime(planId, request.dayNumber(), request.startTime(), request.endTime())) {
            throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
        }

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

    @Transactional
    public PlanDetailResponse updateDetail(Long planId, Long detailId, PlanDetailRequest request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        PlanDetail planDetail = planDetailRepository.findById(detailId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_DETAIL_NOT_FOUND));

        if (!planDetail.getPlan().getId().equals(planId)) {
            throw new CustomException(ExceptionCode.RESOURCE_RELATION_MISMATCH);
        }

        planDetail.updateInfo(request);

        return PlanDetailResponse.fromEntity(planDetail);
    }

    @Transactional
    public List<PlanDetailResponse> updateDetails(Long planId, List<PlanDetailBulkUpdateRequest> request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        List<PlanDetailResponse> response = new ArrayList<>();

        for (PlanDetailBulkUpdateRequest req : request) {
            PlanDetail detail = planDetailRepository.findById(req.detailId())
                    .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_DETAIL_NOT_FOUND));

            if (!detail.getPlan().getId().equals(plan.getId())) {
                throw new CustomException(ExceptionCode.RESOURCE_RELATION_MISMATCH);
            }

            detail.updateInfo(req);
            response.add(PlanDetailResponse.fromEntity(detail));
        }

        return response;
    }

    @Transactional
    public void deleteDetail(Long planId, Long detailId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        PlanDetail detail = planDetailRepository.findById(detailId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_DETAIL_NOT_FOUND));

        if (!detail.getPlan().getId().equals(plan.getId())) {
            throw new CustomException(ExceptionCode.RESOURCE_RELATION_MISMATCH);
        }

        planDetailRepository.delete(detail);
    }

}
