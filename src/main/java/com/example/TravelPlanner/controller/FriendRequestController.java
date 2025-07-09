package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/friends/requests")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("/{friendId}")
    public ResponseEntity<String> sendFriendRequest(
            @LoginMember Member member,
            @PathVariable Long friendId
    ) {
        friendRequestService.sendFriendRequest(member, friendId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("친구 요청이 정상적으로 전송되었습니다.");
    }

}
