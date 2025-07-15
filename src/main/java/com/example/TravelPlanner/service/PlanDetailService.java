package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.PlanDetailBulkUpdateRequest;
import com.example.TravelPlanner.dto.request.PlanDetailRequest;
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

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanDetailService {
    private final PlanDetailRepository planDetailRepository;
    private final PlanRepository planRepository;

    @Transactional
    public PlanDetailResponse createDetail(Long planId, PlanDetailRequest request) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        validateDayNumberRange(plan, request.dayNumber());

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
        if (request == null || request.isEmpty()) {
            throw new CustomException(ExceptionCode.REQUEST_LIST_EMPTY);
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        for (PlanDetailRequest req : request) {
            validateDayNumberRange(plan, req.dayNumber());
        }

        List<PlanDetailRequest> sorted = new ArrayList<>(request);

        sorted.sort(Comparator.comparingInt(PlanDetailRequest::dayNumber)
                .thenComparing(PlanDetailRequest::startTime)
        );

        for (int i = 0; i < sorted.size() - 1; i++) {
            PlanDetailRequest current = sorted.get(i);
            PlanDetailRequest next = sorted.get(i + 1);

            if (current.dayNumber().equals(next.dayNumber()) && current.endTime().isAfter(next.startTime())) {
                throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
            }
        }

        Set<Integer> dayNumbers = request.stream()
                .map(PlanDetailRequest::dayNumber)
                .collect(Collectors.toSet());

        List<PlanDetail> existDetails = planDetailRepository.findAllByPlanIdAndDayNumberIn(planId, dayNumbers);

        Map<Integer, List<PlanDetailRequest>> groupedRequest = request.stream()
                .collect(Collectors.groupingBy(PlanDetailRequest::dayNumber));

        Map<Integer, List<PlanDetail>> groupedExist = existDetails.stream()
                .collect(Collectors.groupingBy(PlanDetail::getDayNumber));

        for (Integer day : groupedRequest.keySet()) {
            List<PlanDetailRequest> requestList = groupedRequest.get(day);
            List<PlanDetail> existList = groupedExist.getOrDefault(day, Collections.emptyList());

            for (PlanDetailRequest req : requestList) {
                for (PlanDetail exist : existList) {
                    if (req.startTime().isBefore(exist.getEndTime()) && req.endTime().isAfter(exist.getStartTime())) {
                        throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
                    }
                }
            }
        }

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

        validateDayNumberRange(plan, request.dayNumber());

        if (!planDetail.getPlan().getId().equals(planId)) {
            throw new CustomException(ExceptionCode.RESOURCE_RELATION_MISMATCH);
        }

        if (planDetailRepository.existsByPlanIdAndDayNumberAndConflictTimeExcludeDetailId(planId, request.dayNumber(), request.startTime(), request.endTime(), detailId)) {
            throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
        }

        planDetail.updateInfo(request);

        return PlanDetailResponse.fromEntity(planDetail);
    }

    @Transactional
    public List<PlanDetailResponse> updateDetails(Long planId, List<PlanDetailBulkUpdateRequest> request) {
        if (request == null || request.isEmpty()) {
            throw new CustomException(ExceptionCode.REQUEST_LIST_EMPTY);
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        for (PlanDetailBulkUpdateRequest req : request) {
            validateDayNumberRange(plan, req.dayNumber());
        }

        List<PlanDetailBulkUpdateRequest> sorted = new ArrayList<>(request);

        sorted.sort(Comparator.comparingInt(PlanDetailBulkUpdateRequest::dayNumber)
                .thenComparing(PlanDetailBulkUpdateRequest::startTime));

        for (int i = 0; i < sorted.size() - 1; i++) {
            PlanDetailBulkUpdateRequest current = sorted.get(i);
            PlanDetailBulkUpdateRequest next = sorted.get(i + 1);

            if (current.dayNumber().equals(next.dayNumber()) && current.endTime().isAfter(next.startTime())) {
                throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
            }
        }

        Set<Long> ids = request.stream()
                .map(PlanDetailBulkUpdateRequest::detailId)
                .collect(Collectors.toSet());

        Set<Integer> dayNumbers = request.stream()
                .map(PlanDetailBulkUpdateRequest::dayNumber)
                .collect(Collectors.toSet());

        List<PlanDetail> existing = planDetailRepository
                .findAllByPlanIdAndDayNumberInAndExcludeIds(planId, dayNumbers, ids);

        for (PlanDetailBulkUpdateRequest req : request) {
            for (PlanDetail exist : existing) {
                if (req.dayNumber().equals(exist.getDayNumber())
                        && req.startTime().isBefore(exist.getEndTime())
                        && req.endTime().isAfter(exist.getStartTime())) {
                    throw new CustomException(ExceptionCode.PLAN_DETAIL_TIME_CONFLICT);
                }
            }
        }

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

    private void validateDayNumberRange(Plan plan, int dayNumber) {
        int travelDay = (int) ChronoUnit.DAYS.between(plan.getStartDate(), plan.getEndDate()) + 1;

        if (dayNumber < 1 || dayNumber > travelDay) {
            throw new CustomException(ExceptionCode.INVALID_DAY_NUMBER);
        }
    }
}
