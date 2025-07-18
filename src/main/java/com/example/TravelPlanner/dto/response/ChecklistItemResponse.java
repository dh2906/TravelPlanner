package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.ChecklistItem;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChecklistItemResponse(
    Long id,
    String name,
    String description,
    boolean isChecked,
    LocalDateTime createdAt,
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
