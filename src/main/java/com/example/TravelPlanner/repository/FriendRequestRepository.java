package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    public boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    public Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long ReceiverId);

    public List<FriendRequest> findAllByReceiverId(Long receiverId);

    public List<FriendRequest> findAllBySenderId(Long senderId);
}
