package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.PlanRequest;
import com.example.TravelPlanner.controller.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

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
}
