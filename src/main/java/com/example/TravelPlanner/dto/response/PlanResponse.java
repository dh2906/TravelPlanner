package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Plan;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PlanResponse(
        Long id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Plan.Visibility visibility,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PlanResponse fromEntity(Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .visibility(plan.getVisibility())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }
}
