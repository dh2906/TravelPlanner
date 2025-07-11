package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import com.example.TravelPlanner.global.annotation.EndTime;
import com.example.TravelPlanner.global.annotation.StartTime;
import com.example.TravelPlanner.global.annotation.ValidDateOrTimeRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@ValidDateOrTimeRange
public record PlanDetailRequest(
        @NotNull Integer dayNumber,

        @NotBlank String placeName,

        @NotBlank String address,

        String memo, // optional

        @NotNull @StartTime LocalTime startTime,

        @NotNull @EndTime LocalTime endTime
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
