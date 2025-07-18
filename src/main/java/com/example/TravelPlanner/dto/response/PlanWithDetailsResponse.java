package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanWithDetailsResponse(
    @Schema(description = "일정 정보")
    PlanResponse plan,

    @Schema(description = "상세 일정 리스트")
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
