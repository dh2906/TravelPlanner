package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.dto.response.FriendResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @GetMapping("/me")
    public ResponseEntity<List<FriendResponse>> getMyFriends(
            @LoginMember Member member
    ) {
        List<FriendResponse> response = friendService.getMyFriends(member);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<String> deleteFriend(
            @LoginMember Member member,
            @PathVariable Long friendId
    ) {
        friendService.deleteFriend(member, friendId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
