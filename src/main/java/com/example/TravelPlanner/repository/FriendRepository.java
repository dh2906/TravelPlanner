package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    public List<Friend> findAllByMemberId(Long memberId);

    public Optional<Friend> findByMemberIdAndFriendId(Long memberId, Long friendId);

    public void deleteByMemberIdAndFriendId(Long memberId, Long friendId);
}
