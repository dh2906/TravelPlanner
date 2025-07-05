package com.example.TravelPlanner.controller.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record PlanDetailResponse(
        Long id,
        int dayNumber,
        int sequence,
        String placeName,
        String address,
        String memo,
        LocalTime startTime,
        LocalTime endTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
