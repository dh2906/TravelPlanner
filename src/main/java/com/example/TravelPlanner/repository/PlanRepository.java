package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {

}
