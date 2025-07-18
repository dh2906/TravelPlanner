package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChecklistItemRequest(
    @NotBlank(message = "항목 이름을 입력해주세요.")
    @Size(max = 100, message = "항목 이름은 최대 100자까지 입력 가능합니다.")
    String name,

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
