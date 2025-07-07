package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.controller.dto.request.PlanRequest;
import com.example.TravelPlanner.controller.dto.response.PlanDetailResponse;
import com.example.TravelPlanner.controller.dto.response.PlanResponse;
import com.example.TravelPlanner.controller.dto.response.PlanWithDetailsResponse;
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
            @LoginMember Member member,
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
            @LoginMember Member member,
            @RequestBody @Valid List<PlanDetailRequest> request
    ) {
        List<PlanDetailResponse> response = planDetailService.createDetails(planId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
