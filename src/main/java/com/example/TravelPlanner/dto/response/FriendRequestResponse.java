package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.FriendRequest;
import lombok.Builder;

@Builder
public record FriendRequestResponse(
    Long id,
    Long memberId,
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
