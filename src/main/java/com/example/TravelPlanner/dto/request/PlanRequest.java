package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.global.annotation.EndDate;
import com.example.TravelPlanner.global.annotation.StartDate;
import com.example.TravelPlanner.global.annotation.ValidDateOrTimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@ValidDateOrTimeRange
public record PlanRequest(
    @Schema(description = "일정 제목", example = "유럽 여행")
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 최대 100자까지 가능합니다.")
    String title,

    @Schema(description = "일정 설명", example = "2025년 여름 유럽 배낭여행 계획")
    String description,

    @Schema(description = "여행 시작일", example = "2025-08-01")
    @NotNull(message = "시작일은 필수 입력값입니다.")
    @StartDate
    LocalDate startDate,

    @Schema(description = "여행 종료일", example = "2025-08-15")
    @NotNull(message = "종료일은 필수 입력값입니다.")
    @EndDate
    LocalDate endDate,

    @Schema(description = "일정 공개 여부", example = "PUBLIC")
    @NotNull(message = "공개 여부는 필수 입력값입니다.")
    Plan.Visibility visibility
) {
    public Plan toEntity(
        Member member
    ) {
        return Plan.builder()
            .member(member)
            .title(title)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .visibility(visibility)
            .build();
    }
}
