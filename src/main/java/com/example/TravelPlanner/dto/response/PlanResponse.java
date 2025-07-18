package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PlanResponse(
    @Schema(description = "일정 ID", example = "1")
    Long id,

    @Schema(description = "멤버 ID", example = "10")
    Long memberId,

    @Schema(description = "일정 제목", example = "여름휴가 일정")
    String title,

    @Schema(description = "일정 설명", example = "가족과 함께하는 5박 6일 여행")
    String description,

    @Schema(description = "시작 날짜", example = "2025-08-01")
    LocalDate startDate,

    @Schema(description = "종료 날짜", example = "2025-08-06")
    LocalDate endDate,

    @Schema(description = "공개 여부", example = "PUBLIC")
    Plan.Visibility visibility,

    @Schema(description = "생성 일시", example = "2025-07-18T10:30:00")
    LocalDateTime createdAt,

    @Schema(description = "수정 일시", example = "2025-07-19T11:45:00")
    LocalDateTime updatedAt
) {
    public static PlanResponse fromEntity(
        Plan plan
    ) {
        return PlanResponse.builder()
            .id(plan.getId())
            .memberId(plan.getMember().getId())
            .title(plan.getTitle())
            .description(plan.getDescription())
            .startDate(plan.getStartDate())
            .endDate(plan.getEndDate())
            .visibility(plan.getVisibility())
            .createdAt(plan.getCreatedAt())
            .updatedAt(plan.getUpdatedAt())
            .build();
    }
}
