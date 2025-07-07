package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    public Optional<Favorite> findByMemberIdAndPlanId(Long memberId, Long planId);
}
