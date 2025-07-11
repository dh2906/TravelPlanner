package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.dto.request.PlanDetailBulkUpdateRequest;
import com.example.TravelPlanner.dto.request.PlanRequest;
import com.example.TravelPlanner.dto.response.PlanDetailResponse;
import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.dto.response.PlanWithDetailsResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.PlanDetailService;
import com.example.TravelPlanner.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    private final PlanDetailService planDetailService;

    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(
            @LoginMember Member member,
            @RequestBody @Valid PlanRequest request
    ) {
        PlanResponse response = planService.createPlan(member, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<PlanResponse>> getMyPlans(
            @LoginMember Member member
    ) {
        List<PlanResponse> response = planService.getPlansByMemberId(member.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getPublicPlans() {
        List<PlanResponse> response = planService.getPublicPlans();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanWithDetailsResponse> getPlanDetailByPlanId(
            @PathVariable Long planId
    ) {
        PlanWithDetailsResponse response = planService.getPlanWithDetailByPlanId(planId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{planId}/details")
    public ResponseEntity<PlanDetailResponse> createPlanDetail(
            @PathVariable Long planId,
            @RequestBody @Valid PlanDetailRequest request
    ) {
        PlanDetailResponse response = planDetailService.createDetail(planId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{planId}/details/bulk")
    public ResponseEntity<List<PlanDetailResponse>> createPlanDetails(
            @PathVariable Long planId,
            @RequestBody List<@Valid PlanDetailRequest> request
    ) {
        List<PlanDetailResponse> response = planDetailService.createDetails(planId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{planId}")
    public ResponseEntity<PlanResponse> updatePlan(
            @PathVariable Long planId,
            @RequestBody @Valid PlanRequest request
    ) {
        PlanResponse response = planService.updatePlan(planId, request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId
    ) {
        planService.deletePlan(planId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{planId}/details/{detailId}")
    public ResponseEntity<PlanDetailResponse> updatePlanDetail(
            @PathVariable Long planId,
            @PathVariable Long detailId,
            @RequestBody @Valid PlanDetailRequest request
    ) {
        PlanDetailResponse response = planDetailService.updateDetail(planId, detailId, request);

        return ResponseEntity
                .ok(response);
    }


    @PutMapping("/{planId}/details/bulk")
    public ResponseEntity<List<PlanDetailResponse>> updatePlanDetails(
            @PathVariable Long planId,
            @RequestBody List<@Valid PlanDetailBulkUpdateRequest> request
    ) {
        List<PlanDetailResponse> response = planDetailService.updateDetails(planId, request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{planId}/details/{detailId}")
    public ResponseEntity<Void> deletePlanDetail(
            @PathVariable Long planId,
            @PathVariable Long detailId
    ) {
        planDetailService.deleteDetail(planId, detailId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
