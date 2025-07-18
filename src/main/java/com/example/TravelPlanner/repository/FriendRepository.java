package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("""
        SELECT f
        FROM Friend f
        JOIN FETCH f.friend
        WHERE f.member.id = :memberId
        """)
    List<Friend> findAllByMemberId(@Param("memberId") Long memberId);

    Optional<Friend> findByMemberIdAndFriendId(
        Long memberId,
        Long friendId
    );

    @Modifying
    @Query(value = """
        DELETE FROM Friend f
        WHERE f.member.id = :memberId
        AND f.friend.id = :friendId
        """)
    void deleteByMemberIdAndFriendId(
        @Param("memberId") Long memberId,
        @Param("friendId") Long friendId
    );

    boolean existsByMemberIdAndFriendId(
        Long memberId,
        Long friendId
    );
}
