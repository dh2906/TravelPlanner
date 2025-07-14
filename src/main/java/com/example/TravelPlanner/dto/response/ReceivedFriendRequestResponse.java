package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.FriendRequest;
import lombok.Builder;

@Builder
public record ReceivedFriendRequestResponse(
        Long id,
        Long senderId,
        String senderName
) {
    public static ReceivedFriendRequestResponse fromEntity(FriendRequest friendRequest) {
        return ReceivedFriendRequestResponse.builder()
                .id(friendRequest.getId())
                .senderId(friendRequest.getSender().getId())
                .senderName(friendRequest.getSender().getName())
                .build();
    }
}
