package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    public boolean existsBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, FriendRequest.Status status);

    public Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long ReceiverId);

    public List<FriendRequest> findAllByReceiverIdAndStatus(Long receiverId, FriendRequest.Status status);

    public List<FriendRequest> findAllBySenderIdAndStatus(Long senderId, FriendRequest.Status status);
}
