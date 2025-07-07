package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Favorite;
import com.example.TravelPlanner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    public Optional<Favorite> findByMemberIdAndPlanId(Long memberId, Long planId);

    public List<Favorite> findAllByMember(Member member);
}
