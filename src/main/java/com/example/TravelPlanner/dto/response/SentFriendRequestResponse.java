package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.FriendRequest;
import lombok.Builder;

@Builder
public record SentFriendRequestResponse(
        Long receiverId,
        String receiverName
) {
    public static SentFriendRequestResponse fromEntity(FriendRequest friendRequest) {
        return SentFriendRequestResponse.builder()
                .receiverId(friendRequest.getReceiver().getId())
                .receiverName(friendRequest.getReceiver().getName())
                .build();
    }
}
