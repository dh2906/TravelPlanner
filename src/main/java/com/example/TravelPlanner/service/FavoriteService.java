package com.example.TravelPlanner.service;

import com.example.TravelPlanner.entity.Favorite;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.FavoriteRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    public boolean toggleFavorite(Long memberId, Long planId) {
        Favorite favorite = favoriteRepository.findByMemberIdAndPlanId(memberId, planId)
                .orElse(null);

        if (favorite != null) {
            favoriteRepository.delete(favorite);
            return false;
        } else {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

            Plan plan = planRepository.findById(planId)
                    .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

            favoriteRepository.save(
                    Favorite.builder()
                            .member(member)
                            .plan(plan)
                            .build()
            );

            return true;
        }
    }
}
