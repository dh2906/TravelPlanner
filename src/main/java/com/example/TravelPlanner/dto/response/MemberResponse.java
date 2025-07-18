package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MemberResponse(
    @Schema(description = "회원 ID", example = "1")
    Long id,

    @Schema(description = "회원 이메일", example = "user@example.com")
    String email,

    @Schema(description = "회원 이름", example = "홍길동")
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
