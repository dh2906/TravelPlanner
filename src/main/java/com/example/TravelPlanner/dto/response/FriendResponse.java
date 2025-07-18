package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Friend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FriendResponse(
    @Schema(description = "친구 회원 ID", example = "123")
    Long id,

    @Schema(description = "친구 회원 이름", example = "홍길동")
    String name
) {
    public static FriendResponse fromEntity(
        Friend friend
    ) {
        return FriendResponse.builder()
            .id(friend.getFriend().getId())
            .name(friend.getFriend().getName())
            .build();
    }
}
