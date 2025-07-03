package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
