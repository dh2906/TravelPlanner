package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.global.annotation.EndTime;
import com.example.TravelPlanner.global.annotation.StartTime;
import com.example.TravelPlanner.global.annotation.ValidDateOrTimeRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@ValidDateOrTimeRange
public record PlanDetailBulkUpdateRequest(
        @NotNull Long detailId,          // 수정 대상 상세 일정 ID
        @NotNull Integer dayNumber,
        @NotBlank String placeName,
        @NotBlank String address,
        String memo,
        @NotNull @StartTime LocalTime startTime,
        @NotNull @EndTime LocalTime endTime
) {

}
