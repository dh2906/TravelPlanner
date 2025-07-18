package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.api.FriendRequestApi;
import com.example.TravelPlanner.dto.response.FriendRequestResponse;
import com.example.TravelPlanner.global.annotation.resolver.LoginMember;
import com.example.TravelPlanner.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendRequestController implements FriendRequestApi {
    private final FriendRequestService friendRequestService;

    @PostMapping("/{friendId}")
    public ResponseEntity<String> sendFriendRequest(
        @LoginMember Long memberId,
        @PathVariable Long friendId
    ) {
        friendRequestService.sendFriendRequest(memberId, friendId);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("친구 요청이 전송되었습니다.");
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> cancelFriendRequest(
        @LoginMember Long memberId,
        @PathVariable Long friendId
    ) {
        friendRequestService.cancelFriendRequest(memberId, friendId);

        return ResponseEntity
            .ok("친구 요청이 취소되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<FriendRequestResponse>> getFriendRequests(
        @LoginMember Long memberId,
        @RequestParam String type
    ) {
        List<FriendRequestResponse> response = friendRequestService.getFriendRequests(memberId, type);

        return ResponseEntity
            .ok(response);
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<String> acceptFriendRequest(
        @LoginMember Long memberId,
        @PathVariable Long requestId
    ) {
        friendRequestService.acceptFriendRequest(memberId, requestId);

        return ResponseEntity
            .ok("친구 요청을 수락하였습니다.");
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<String> rejectFriendRequest(
        @LoginMember Long memberId,
        @PathVariable Long requestId
    ) {
        friendRequestService.rejectFriendRequest(memberId, requestId);

        return ResponseEntity
            .ok("친구 요청을 거절하였습니다.");
    }
}
