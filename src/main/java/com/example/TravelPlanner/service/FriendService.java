package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.response.FriendResponse;
import com.example.TravelPlanner.entity.Friend;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
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
    public List<FriendResponse> getMyFriends(Long memberId) {
        List<Friend> friends = friendRepository.findAllByMemberId(memberId);

        return friends.stream()
                .map(FriendResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteFriend(Long memberId, Long friendId) {
        Friend friend = friendRepository.findByMemberIdAndFriendId(memberId, friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_RELATION_NOT_FOUND));

        friendRepository.deleteByMemberIdAndFriendId(memberId, friendId);
        friendRepository.deleteByMemberIdAndFriendId(friendId, memberId);
    }
}
