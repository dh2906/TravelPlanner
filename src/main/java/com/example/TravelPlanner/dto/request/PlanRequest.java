package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.global.annotation.EndDate;
import com.example.TravelPlanner.global.annotation.StartDate;
import com.example.TravelPlanner.global.annotation.ValidDateOrTimeRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@ValidDateOrTimeRange
public record PlanRequest(
        @NotBlank(message = "제목은 필수 입력값입니다.")
        @Size(max = 100, message = "제목은 최대 100자까지 가능합니다.")
        String title,

        String description,

        @NotNull(message = "시작일은 필수 입력값입니다.")
        @StartDate
        LocalDate startDate,

        @NotNull(message = "종료일은 필수 입력값입니다.")
        @EndDate
        LocalDate endDate,

        @NotNull(message = "공개 여부는 필수 입력값입니다.")
        Plan.Visibility visibility
) {
    public Plan toEntity(Member member) {
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
