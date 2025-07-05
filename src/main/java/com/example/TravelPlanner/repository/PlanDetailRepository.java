package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {
    public List<PlanDetail> findAllByPlanId(Long planId);
}
