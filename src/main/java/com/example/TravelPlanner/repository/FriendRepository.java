package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    public List<Friend> findAllByMemberId(Long memberId);
}
