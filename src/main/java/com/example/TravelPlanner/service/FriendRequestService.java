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
    public void sendFriendRequest(Member sender, Long friendId) {
        if (sender.getId().equals(friendId)) {
            throw new CustomException(ExceptionCode.INVALID_FRIEND_REQUEST);
        }

        Member receiver = memberRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        if (friendRepository.existsByMemberIdAndFriendId(sender.getId(), receiver.getId())) {
            throw new CustomException(ExceptionCode.FRIEND_ALREADY_EXISTS);
        }

        if (friendRequestRepository.existsBySenderIdAndReceiverId(sender.getId(), receiver.getId())) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_SENT);
        }

        friendRequestRepository.findBySenderIdAndReceiverId(receiver.getId(), sender.getId())
                               .ifPresent(request -> {
                                   if (request.getStatus() == FriendRequest.Status.PENDING) {
                                       throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_RECEIVED);
                                   }
                               });

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        friendRequestRepository.save(request);
    }

    @Transactional
    public void cancelFriendRequest(Member sender, Long friendId) {
        FriendRequest request = friendRequestRepository
                .findBySenderIdAndReceiverId(sender.getId(), friendId)
                .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_REQUEST_NOT_FOUND));

        if (request.getStatus() != FriendRequest.Status.PENDING) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_PROCESSED);
        }

        friendRequestRepository.delete(request);
    }

    @Transactional(readOnly = true)
    public List<ReceivedFriendRequestResponse> getPendingReceivedFriendRequests(Member member) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByReceiverIdAndStatus(member.getId(), FriendRequest.Status.PENDING);

        return friendRequests.stream()
                .map(ReceivedFriendRequestResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SentFriendRequestResponse> getSentFriendRequests(Member member) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllBySenderIdAndStatus(member.getId(), FriendRequest.Status.PENDING);

        return friendRequests.stream()
                .map(SentFriendRequestResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void acceptFriendRequest(Member receiver, Long requestId) {
        FriendRequest friendRequest = findFriendRequestOrThrow(requestId);
        validateOwnership(receiver, friendRequest);

        Member sender = friendRequest.getSender();

        friendRequest.acceptOrThrow();

        friendRepository.save(Friend.create(receiver, sender));
        friendRepository.save(Friend.create(sender, receiver));
    }

    @Transactional
    public void rejectFriendRequest(Member receiver, Long requestId) {
        FriendRequest friendRequest = findFriendRequestOrThrow(requestId);
        validateOwnership(receiver, friendRequest);

        friendRequest.rejectOrThrow();
    }

    private FriendRequest findFriendRequestOrThrow(Long requestId) {
        return friendRequestRepository.findById(requestId)
                                      .orElseThrow(() -> new CustomException(ExceptionCode.FRIEND_REQUEST_NOT_FOUND));
    }

    private void validateOwnership(Member receiver, FriendRequest friendRequest) {
        if (!friendRequest.getReceiver()
                          .getId()
                          .equals(receiver.getId())
        ) {
            throw new CustomException(ExceptionCode.INVALID_FRIEND_REQUEST);
        }
    }
}
