package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.api.FriendApi;
import com.example.TravelPlanner.dto.response.FriendResponse;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController implements FriendApi {
    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(
            @LoginMember Long memberId
    ) {
        List<FriendResponse> response = friendService.getFriends(memberId);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(
            @LoginMember Long memberId,
            @PathVariable Long friendId
    ) {
        friendService.deleteFriend(memberId, friendId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
