package com.example.TravelPlanner.repository;

import com.example.TravelPlanner.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByEmail(String email);

    public boolean existsByEmail(String email);
}
