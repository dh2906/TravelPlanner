package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.PlanDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record PlanDetailResponse(
    @Schema(description = "상세 일정 ID", example = "1")
    Long id,

    @Schema(description = "여행 일차", example = "2")
    Integer dayNumber,

    @Schema(description = "장소 이름", example = "서울역")
    String placeName,

    @Schema(description = "주소", example = "서울특별시 중구 서울역로 123")
    String address,

    @Schema(description = "메모", example = "맛집 방문 예정")
    String memo,

    @Schema(description = "시작 시간", example = "09:00:00")
    LocalTime startTime,

    @Schema(description = "종료 시간", example = "11:00:00")
    LocalTime endTime,

    @Schema(description = "생성 일시", example = "2025-07-18T15:30:00")
    LocalDateTime createdAt,

    @Schema(description = "수정 일시", example = "2025-07-19T10:20:00")
    LocalDateTime updatedAt
) {
    public static PlanDetailResponse fromEntity(
        PlanDetail planDetail
    ) {
        return PlanDetailResponse.builder()
            .id(planDetail.getId())
            .dayNumber(planDetail.getDayNumber())
            .placeName(planDetail.getPlaceName())
            .address(planDetail.getAddress())
            .memo(planDetail.getMemo())
            .startTime(planDetail.getStartTime())
            .endTime(planDetail.getEndTime())
            .createdAt(planDetail.getCreatedAt())
            .updatedAt(planDetail.getUpdatedAt())
            .build();
    }
}
