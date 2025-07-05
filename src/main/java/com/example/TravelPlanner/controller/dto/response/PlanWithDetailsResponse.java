package com.example.TravelPlanner.controller.dto.response;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanWithDetailsResponse(
        PlanResponse plan,
        List<PlanDetail> planDetailsResponses
) {
    public static PlanWithDetailsResponse fromEntities(
            Plan plan,
            List<PlanDetail> planDetails
    ) {
        return PlanWithDetailsResponse.builder()
                .plan(PlanResponse.fromEntity(plan))
                .planDetailsResponses(planDetails)
                .build();
    }
}
