package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChecklistItemRequest(
    @Schema(description = "체크리스트 항목 이름", example = "세면도구 챙기기")
    @NotBlank(message = "항목 이름을 입력해주세요.")
    @Size(max = 100, message = "항목 이름은 최대 100자까지 입력 가능합니다.")
    String name,

    @Schema(description = "체크리스트 항목 설명", example = "칫솔, 치약, 수건 등 포함")
    String description
) {
    public ChecklistItem toEntity(
        Member member
    ) {
        return ChecklistItem.builder()
            .member(member)
            .name(name)
            .description(description)
            .build();
    }

}
