package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    public List<ChecklistItem> findAllByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE FROM ChecklistItem c
            WHERE c.member.id = :memberId
            """)
    public void deleteAllByMemberId(@Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE ChecklistItem c
            SET c.checked = false
            WHERE c.member.id = :memberId
            """)
    public void clearCheckedAllItems(@Param("memberId") Long memberId);
}
