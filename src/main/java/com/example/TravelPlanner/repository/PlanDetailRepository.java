package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {
    List<PlanDetail> findAllByPlanId(Long planId);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM PlanDetail as pd
                WHERE pd.plan.id = :planId
                AND pd.dayNumber = :dayNumber
                AND pd.startTime < :endTime
                AND pd.endTime > :startTime
            )
            """)
    boolean existsByPlanIdAndDayNumberAndConflictTime(
            @Param("planId") Long planId,
            @Param("dayNumber") Integer dayNumber,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM PlanDetail as pd
                WHERE pd.plan.id = :planId
                AND pd.id <> :detailId
                AND pd.dayNumber = :dayNumber
                AND pd.startTime < :endTime
                AND pd.endTime > :startTime
            )
            """)
    boolean existsByPlanIdAndDayNumberAndConflictTimeExcludeDetailId(
            @Param("planId") Long planId,
            @Param("dayNumber") Integer dayNumber,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("detailId") Long detailId
    );

    List<PlanDetail> findAllByPlanIdAndDayNumberIn(
            Long planId,
            Collection<Integer> dayNumbers
    );

    @Query(value = """
            SELECT pd
            FROM PlanDetail pd
            WHERE pd.plan.id = :planId
            AND pd.dayNumber in :dayNumbers
            AND pd.id NOT IN :ids
            """)
    List<PlanDetail> findAllByPlanIdAndDayNumberInAndExcludeIds(
            @Param("planId") Long planId,
            @Param("dayNumbers") Collection<Integer> dayNumbers,
            @Param("ids") Collection<Long> ids
    );

    List<PlanDetail> findAllByPlanIdOrderByDayNumberAscStartTimeAsc(Long planId);
}
