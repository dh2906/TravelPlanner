package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String email,
        String name
) {
    public static MemberResponse fromEntity(
            Member member
    ) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
