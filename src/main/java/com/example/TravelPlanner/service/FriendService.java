package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.response.FriendResponse;
import com.example.TravelPlanner.entity.Friend;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional(readOnly = true)
    public List<FriendResponse> getMyFriends(Member member) {
        List<Friend> friends = friendRepository.findAllByMemberId(member.getId());

        return friends.stream()
                .map(FriendResponse::fromEntity)
                .toList();
    }
}
