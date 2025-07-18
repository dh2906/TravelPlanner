package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByMemberId(Long id);

    List<Plan> findAllByVisibility(Plan.Visibility visibility);

    Optional<Plan> findBySharePath(String sharePath);

    @Query(value = """
            SELECT p
            FROM Plan p
            WHERE p.visibility = 'PUBLIC'
            AND (
                LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
        """)
    List<Plan> searchPublicPlansByKeyword(@Param("keyword") String keyword);
}
