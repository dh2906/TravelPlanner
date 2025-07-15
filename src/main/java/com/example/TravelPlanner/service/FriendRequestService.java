package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.response.ReceivedFriendRequestResponse;
import com.example.TravelPlanner.dto.response.SentFriendRequestResponse;
import com.example.TravelPlanner.entity.Friend;
import com.example.TravelPlanner.entity.FriendRequest;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.FriendRepository;
import com.example.TravelPlanner.repository.FriendRequestRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final MemberRepository memberRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new CustomException(ExceptionCode.INVALID_FRIEND_REQUEST);
        }

        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        if (friendRepository.existsByMemberIdAndFriendId(senderId, receiverId)) {
            throw new CustomException(ExceptionCode.FRIEND_ALREADY_EXISTS);
        }

        if (friendRequestRepository.existsBySenderIdAndReceiverIdAndStatus(senderId, receiverId, FriendRequest.Status.PENDING)) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_SENT);
        }

        if (friendRequestRepository.existsBySenderIdAndReceiverIdAndStatus(receiverId, senderId, FriendRequest.Status.PENDING)) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_RECEIVED);
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        friendRequestRepository.save(request);
    }

    @Transactional
    public void cancelFriendRequest(Long senderId, Long friendId) {
        FriendRequest request = friendRequestRepository
                .findBySenderIdAndReceiverId(senderId, friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_REQUEST_NOT_FOUND));

        if (request.getStatus() != FriendRequest.Status.PENDING) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_PROCESSED);
        }

        friendRequestRepository.delete(request);
    }

    @Transactional(readOnly = true)
    public List<ReceivedFriendRequestResponse> getPendingReceivedFriendRequests(Long memberId) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByReceiverIdAndStatus(memberId, FriendRequest.Status.PENDING);

        return friendRequests.stream()
                .map(ReceivedFriendRequestResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SentFriendRequestResponse> getSentFriendRequests(Long memberId) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllBySenderIdAndStatus(memberId, FriendRequest.Status.PENDING);

        return friendRequests.stream()
                .map(SentFriendRequestResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Long receiverId, Long requestId) {
        FriendRequest friendRequest = findFriendRequestOrThrow(requestId);
        validateOwnership(receiverId, friendRequest);

        Member receiver = friendRequest.getReceiver();
        Member sender = friendRequest.getSender();

        friendRequest.acceptOrThrow();
        friendRequestRepository.delete(friendRequest);

        friendRepository.save(Friend.create(receiver, sender));
        friendRepository.save(Friend.create(sender, receiver));
    }

    @Transactional
    public void rejectFriendRequest(Long receiverId, Long requestId) {
        FriendRequest friendRequest = findFriendRequestOrThrow(requestId);
        validateOwnership(receiverId, friendRequest);

        friendRequest.rejectOrThrow();
    }

    private FriendRequest findFriendRequestOrThrow(Long requestId) {
        return friendRequestRepository.findById(requestId)
                                      .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_REQUEST_NOT_FOUND));
    }

    private void validateOwnership(Long receiverId, FriendRequest friendRequest) {
        if (!friendRequest.getReceiver()
                          .getId()
                          .equals(receiverId)
        ) {
            throw new CustomException(ExceptionCode.INVALID_FRIEND_REQUEST);
        }
    }
}
