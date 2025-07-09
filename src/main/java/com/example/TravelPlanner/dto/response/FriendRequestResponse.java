package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.FriendRequest;
import lombok.Builder;

@Builder
public record FriendRequestResponse(
        Long senderId,
        String senderName
) {
    public static FriendRequestResponse fromEntity(FriendRequest friendRequest) {
        return FriendRequestResponse.builder()
                .senderId(friendRequest.getSender().getId())
                .senderName(friendRequest.getSender().getName())
                .build();
    }
}
