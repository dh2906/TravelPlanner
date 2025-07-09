package com.example.TravelPlanner.dto.response;

import com.example.TravelPlanner.entity.Friend;
import lombok.Builder;

@Builder
public record FriendResponse(
        Long id,
        String name
) {
    public static FriendResponse fromEntity(Friend friend) {
        return FriendResponse.builder()
                .id(friend.getFriend().getId())
                .name(friend.getFriend().getName())
                .build();
    }
}
