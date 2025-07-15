package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, FriendRequest.Status status);

    Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long ReceiverId);

    @Query(value = """
            SELECT fr
            FROM FriendRequest fr
            JOIN FETCH fr.sender
            WHERE fr.receiver.id = :receiverId
            AND fr.status = :status
            """)
    List<FriendRequest> findAllByReceiverIdAndStatusWithMember(
            @Param("receiverId") Long receiverId,
            @Param("status") FriendRequest.Status status
    );

    @Query(value = """
            SELECT fr
            FROM FriendRequest fr
            JOIN FETCH fr.receiver
            WHERE fr.sender.id = :senderId
            """)
    List<FriendRequest> findAllBySenderIdAndStatusWithMember(
            @Param("senderId") Long senderId,
            @Param("status") FriendRequest.Status status
    );
}
