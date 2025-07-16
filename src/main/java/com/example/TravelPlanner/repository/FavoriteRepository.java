package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberIdAndPlanId(
            Long memberId,
            Long planId
    );

    @Query(value = """
            SELECT f
            FROM Favorite f
            JOIN FETCH f.plan p
            WHERE f.member.id = :memberId
            """)
    List<Favorite> findAllByMemberIdWithPlan(@Param("memberId") Long memberId);
}
