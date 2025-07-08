package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<ChecklistItem, Long> {
    public List<ChecklistItem> findAllByMember(Member member);
}
