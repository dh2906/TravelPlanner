package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.ChecklistItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChecklistItemResponse(
    @Schema(description = "체크리스트 항목 ID", example = "1")
    Long id,

    @Schema(description = "체크리스트 항목 이름", example = "짐 싸기")
    String name,

    @Schema(description = "체크리스트 항목 설명", example = "옷, 세면도구 챙기기")
    String description,

    @Schema(description = "체크 여부", example = "true")
    boolean isChecked,

    @Schema(description = "생성 시각", example = "2025-07-18T14:30:00")
    LocalDateTime createdAt,

    @Schema(description = "수정 시각", example = "2025-07-18T15:00:00")
    LocalDateTime updatedAt
) {
    public static ChecklistItemResponse fromEntity(
        ChecklistItem checklistItem
    ) {
        return ChecklistItemResponse.builder()
            .id(checklistItem.getId())
            .name(checklistItem.getName())
            .isChecked(checklistItem.isChecked())
            .description(checklistItem.getDescription())
            .createdAt(checklistItem.getCreatedAt())
            .updatedAt(checklistItem.getUpdatedAt())
            .build();
    }
}
