package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.PlanDetail;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record PlanDetailResponse(
        Long id,
        int dayNumber,
        String placeName,
        String address,
        String memo,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PlanDetailResponse fromEntity(PlanDetail planDetail) {
        return PlanDetailResponse.builder()
                .id(planDetail.getId())
                .dayNumber(planDetail.getDayNumber())
                .placeName(planDetail.getPlaceName())
                .address(planDetail.getAddress())
                .memo(planDetail.getMemo())
                .startTime(planDetail.getStartTime())
                .endTime(planDetail.getEndTime())
                .createdAt(planDetail.getCreatedAt())
                .updatedAt(planDetail.getUpdatedAt())
                .build();
    }
}
