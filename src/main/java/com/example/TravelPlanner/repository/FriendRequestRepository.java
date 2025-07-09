package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    public boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
