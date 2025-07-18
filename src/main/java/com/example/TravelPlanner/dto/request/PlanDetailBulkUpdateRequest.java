package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.global.annotation.validation.EndTime;
import com.example.TravelPlanner.global.annotation.validation.StartTime;
import com.example.TravelPlanner.global.annotation.validation.ValidDateOrTimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@ValidDateOrTimeRange
public record PlanDetailBulkUpdateRequest(
    @Schema(description = "수정할 상세 일정 ID", example = "1")
    @NotNull(message = "상세 일정 id를 입력해주세요.")
    Long detailId,

    @Schema(description = "여행 일차 (1일부터 시작)", example = "1")
    @NotNull(message = "여행 일차를 입력해주세요.")
    Integer dayNumber,

    @Schema(description = "장소 이름", example = "경복궁")
    @NotBlank(message = "장소 명을 입력해주세요.")
    String placeName,

    @Schema(description = "주소", example = "서울특별시 종로구 사직로 161")
    @NotBlank(message = "주소를 입력해주세요.")
    String address,

    @Schema(description = "메모", example = "입장권 필요 없음")
    String memo,

    @Schema(description = "시작 시간", example = "09:00")
    @NotNull(message = "시작 시간을 입력해주세요")
    @StartTime
    LocalTime startTime,

    @Schema(description = "종료 시간", example = "11:00")
    @NotNull(message = "종료 시간을 입력해주세요")
    @EndTime
    LocalTime endTime
) {

}
