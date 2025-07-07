package com.example.TravelPlanner.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record PlanDetailsUpdateRequest(
        @NotNull Long detailId,          // 수정 대상 상세 일정 ID
        @NotNull Integer dayNumber,
        @NotBlank String placeName,
        @NotBlank String address,
        String memo,
        @NotNull LocalTime startTime,
        @NotNull LocalTime endTime
) {

}
