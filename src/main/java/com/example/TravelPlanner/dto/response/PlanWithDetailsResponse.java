package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanWithDetailsResponse(
    PlanResponse plan,
    List<PlanDetailResponse> planDetailsResponses
) {
    public static PlanWithDetailsResponse fromEntities(
        Plan plan,
        List<PlanDetail> planDetails
    ) {
        return PlanWithDetailsResponse.builder()
            .plan(PlanResponse.fromEntity(plan))
            .planDetailsResponses(
                planDetails.stream()
                    .map(PlanDetailResponse::fromEntity)
                    .toList()
            )
            .build();
    }
}
