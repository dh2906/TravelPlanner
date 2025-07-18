package com.example.TravelPlanner;

import com.example.TravelPlanner.entity.FriendRequest;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.repository.FriendRepository;
import com.example.TravelPlanner.repository.FriendRequestRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.service.FriendRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FriendRequestServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendRequestService friendRequestService;

    @Test
    void 친구요청_전송_성공() {
        Long senderId = 1L;
        Long receiverId = 2L;
        Member sender = Member.builder().id(senderId).build();
        Member receiver = Member.builder().id(receiverId).build();

        given(memberRepository.findById(senderId)).willReturn(Optional.of(sender));
        given(memberRepository.findById(receiverId)).willReturn(Optional.of(receiver));
        given(friendRepository.existsByMemberIdAndFriendId(senderId, receiverId)).willReturn(false);
        given(friendRequestRepository.existsBySenderIdAndReceiverIdAndStatus(senderId, receiverId, FriendRequest.Status.PENDING)).willReturn(false);
        given(friendRequestRepository.existsBySenderIdAndReceiverIdAndStatus(receiverId, senderId, FriendRequest.Status.PENDING)).willReturn(false);

        friendRequestService.sendFriendRequest(senderId, receiverId);

        verify(friendRequestRepository).save(any(FriendRequest.class));
    }

    @Test
    void 전송한_친구요청_목록_조회_성공() {
        Long memberId = 1L;

        Member sender = Member.builder()
            .id(1L)
            .build();

        Member receiver = Member.builder()
            .id(2L)
            .build();

        FriendRequest fr1 = FriendRequest.builder()
            .id(1L)
            .sender(sender)
            .receiver(receiver)
            .build();

        FriendRequest fr2 = FriendRequest.builder()
            .id(2L)
            .sender(receiver)
            .receiver(sender)
            .build();

        given(friendRequestRepository.findAllBySenderIdAndStatusWithMember(memberId, FriendRequest.Status.PENDING))
            .willReturn(List.of(fr1, fr2));

        var responses = friendRequestService.getFriendRequests(memberId, "sent");

        assertThat(responses).hasSize(2);
        verify(friendRequestRepository).findAllBySenderIdAndStatusWithMember(memberId, FriendRequest.Status.PENDING);
    }

    @Test
    void 친구요청_수락_성공() {
        Long receiverId = 2L;
        Long requestId = 10L;
        Member sender = Member.builder().id(1L).build();
        Member receiver = Member.builder().id(receiverId).build();

        FriendRequest friendRequest = FriendRequest.builder()
            .id(requestId)
            .sender(sender)
            .receiver(receiver)
            .status(FriendRequest.Status.PENDING)
            .build();

        given(friendRequestRepository.findById(requestId)).willReturn(Optional.of(friendRequest));

        friendRequestService.acceptFriendRequest(receiverId, requestId);

        assertThat(friendRequest.getStatus()).isEqualTo(FriendRequest.Status.ACCEPTED);

        verify(friendRequestRepository).delete(friendRequest);
        verify(friendRepository, times(2)).save(any());
    }

    @Test
    void 친구요청_거절_성공() {
        Long receiverId = 2L;
        Long requestId = 10L;
        Member sender = Member.builder().id(1L).build();
        Member receiver = Member.builder().id(receiverId).build();

        FriendRequest friendRequest = FriendRequest.builder()
            .id(requestId)
            .sender(sender)
            .receiver(receiver)
            .status(FriendRequest.Status.PENDING)
            .build();

        given(friendRequestRepository.findById(requestId)).willReturn(Optional.of(friendRequest));

        friendRequestService.rejectFriendRequest(receiverId, requestId);

        assertThat(friendRequest.getStatus()).isEqualTo(FriendRequest.Status.REJECTED);
    }
}
