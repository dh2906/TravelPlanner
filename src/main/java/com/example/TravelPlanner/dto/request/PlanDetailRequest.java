package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.entity.PlanDetail;
import com.example.TravelPlanner.global.annotation.validation.EndTime;
import com.example.TravelPlanner.global.annotation.validation.StartTime;
import com.example.TravelPlanner.global.annotation.validation.ValidDateOrTimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@ValidDateOrTimeRange
public record PlanDetailRequest(
    @Schema(description = "여행 일차 (1일부터 시작)", example = "1")
    @NotNull(message = "여행 일차를 입력해주세요.")
    Integer dayNumber,

    @Schema(description = "장소명", example = "에펠탑")
    @NotBlank(message = "장소 명을 입력해주세요.")
    String placeName,

    @Schema(description = "주소", example = "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France")
    @NotBlank(message = "주소를 입력해주세요.")
    String address,

    @Schema(description = "메모", example = "야경이 멋짐")
    String memo,

    @Schema(description = "시작 시간 (HH:mm)", example = "10:00")
    @NotNull(message = "시작 시간을 입력해주세요")
    @StartTime
    LocalTime startTime,

    @Schema(description = "종료 시간 (HH:mm)", example = "12:00")
    @NotNull(message = "종료 시간을 입력해주세요")
    @EndTime
    LocalTime endTime
) {
    public PlanDetail toEntity(
        Plan plan
    ) {
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
