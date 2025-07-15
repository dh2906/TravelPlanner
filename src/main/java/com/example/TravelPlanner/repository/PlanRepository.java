package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByMemberId(Long id);

    List<Plan> findAllByVisibility(Plan.Visibility visibility);
}
