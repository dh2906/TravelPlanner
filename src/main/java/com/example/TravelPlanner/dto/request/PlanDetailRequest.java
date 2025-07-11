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
        @NotNull(message = "여행 일차를 입력해주세요.")
        Integer dayNumber,

        @NotBlank(message = "장소 명을 입력해주세요.")
        String placeName,

        @NotBlank(message = "주소를 입력해주세요.")
        String address,

        String memo,

        @NotNull(message = "시작 시간을 입력해주세요")
        @StartTime
        LocalTime startTime,

        @NotNull(message = "종료 시간을 입력해주세요")
        @EndTime
        LocalTime endTime
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
