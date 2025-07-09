package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record PlanDetailRequest(
        @NotNull Integer dayNumber,

        @NotBlank String placeName,

        @NotBlank String address,

        String memo, // optional

        @NotNull LocalTime startTime,

        @NotNull LocalTime endTime
) {
    public PlanDetail toEntity(Plan plan) {
        return PlanDetail.builder()
                .plan(plan)
                .dayNumber(dayNumber)
                .placeName(placeName)
                .address(address)
                .memo(memo)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
