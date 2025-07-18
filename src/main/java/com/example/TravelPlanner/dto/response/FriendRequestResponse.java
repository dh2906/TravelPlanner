package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.FriendRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FriendRequestResponse(
    @Schema(description = "친구 요청 ID", example = "1")
    Long id,

    @Schema(description = "상대 회원 ID", example = "4")
    Long memberId,

    @Schema(description = "상대 회원 이름", example = "홍길동")
    String memberName
) {
    public static FriendRequestResponse fromEntity(
        FriendRequest friendRequest
    ) {
        return FriendRequestResponse.builder()
            .id(friendRequest.getId())
            .memberId(friendRequest.getSender().getId())
            .memberName(friendRequest.getSender().getName())
            .build();
    }

    public static FriendRequestResponse fromEntity(
        FriendRequest friendRequest,
        String type
    ) {
        Long targetId = null;
        String targetName = null;

        if ("sent".equalsIgnoreCase(type)) {
            targetId = friendRequest.getReceiver().getId();
            targetName = friendRequest.getReceiver().getName();
        } else if ("received".equalsIgnoreCase(type)) {
            targetId = friendRequest.getSender().getId();
            targetName = friendRequest.getSender().getName();
        }

        return FriendRequestResponse.builder()
            .id(friendRequest.getId())
            .memberId(targetId)
            .memberName(targetName)
            .build();
    }
}
