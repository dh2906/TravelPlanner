package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.request.PlanRequest;
import com.example.TravelPlanner.controller.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
