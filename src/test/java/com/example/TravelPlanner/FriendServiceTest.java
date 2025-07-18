package com.example.TravelPlanner;

import com.example.TravelPlanner.dto.response.FriendResponse;
import com.example.TravelPlanner.entity.Friend;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.FriendRepository;
import com.example.TravelPlanner.service.FriendService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {
    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendService friendService;

    @Test
    void 친구목록_조회_성공() {
        Long memberId = 1L;

        Member member = Member.builder()
            .id(memberId)
            .build();

        Member friendMember = Member.builder()
            .id(2L)
            .name("friend")
            .build();

        Friend friend = Friend.builder()
            .member(member)
            .friend(friendMember)
            .build();

        given(friendRepository.findAllByMemberId(memberId)).willReturn(List.of(friend));

        List<FriendResponse> result = friendService.getFriends(memberId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("friend");
    }

    @Test
    void 친구삭제_성공() {
        Long memberId = 1L;
        Long friendId = 2L;

        Friend dummyFriend = Friend.builder()
            .member(Member.builder().id(memberId).build())
            .friend(Member.builder().id(friendId).build())
            .build();

        given(friendRepository.findByMemberIdAndFriendId(memberId, friendId)).willReturn(Optional.of(dummyFriend));

        friendService.deleteFriend(memberId, friendId);

        then(friendRepository).should(times(1)).deleteByMemberIdAndFriendId(memberId, friendId);
        then(friendRepository).should(times(1)).deleteByMemberIdAndFriendId(friendId, memberId);
    }

    @Test
    void 친구삭제_실패_존재하지_않는_관계() {
        Long memberId = 1L;
        Long friendId = 2L;

        given(friendRepository.findByMemberIdAndFriendId(memberId, friendId))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> friendService.deleteFriend(memberId, friendId))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.FRIEND_RELATION_NOT_FOUND.getMessage());
    }
}
