package com.example.TravelPlanner.service;

import com.example.TravelPlanner.entity.FriendRequest;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.FriendRequestRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final MemberRepository memberRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Transactional
    public void sendFriendRequest(Member sender, Long friendId) {
        if (sender.getId().equals(friendId)) {
            throw new CustomException(ExceptionCode.INVALID_FRIEND_REQUEST);
        }

        Member receiver = memberRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        if (friendRequestRepository.existsBySenderIdAndReceiverId(sender.getId(), receiver.getId())) {
            throw new CustomException(ExceptionCode.DUPLICATE_FRIEND_REQUEST);
        }

        if (friendRequestRepository.existsBySenderIdAndReceiverId(receiver.getId(), sender.getId())) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_RECEIVED);
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        friendRequestRepository.save(request);
    }

    @Transactional
    public void cancleFriendRequest(Member sender, Long friendId) {
        FriendRequest request = friendRequestRepository
                .findBySenderIdAndReceiverId(sender.getId(), friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_REQUEST_NOT_FOUND));

        friendRequestRepository.delete(request);
    }
}
