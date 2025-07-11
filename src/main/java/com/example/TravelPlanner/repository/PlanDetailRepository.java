package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {
    public List<PlanDetail> findAllByPlanId(Long planId);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM PlanDetail as pd
                WHERE pd.plan.id = :planId
                AND pd.startTime < :endTime
                AND pd.endTime > :startTime
            )
            """)
    public boolean existsByPlanIdAndConflictTime(
            @Param("planId") Long planId,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
