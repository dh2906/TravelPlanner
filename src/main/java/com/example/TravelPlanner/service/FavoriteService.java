package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.response.PlanResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    @Transactional
    public boolean toggleFavorite(Long memberId, Long planId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionCode.PLAN_NOT_FOUND));

        if (!plan.getMember().getId().equals(memberId) && plan.getVisibility() != Plan.Visibility.PUBLIC) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        Favorite favorite = favoriteRepository.findByMemberIdAndPlanId(memberId, planId)
                .orElse(null);

        if (favorite != null) {
            favoriteRepository.delete(favorite);
            return false;
        } else {
            favoriteRepository.save(
                    Favorite.builder()
                            .member(member)
                            .plan(plan)
                            .build()
            );

            return true;
        }
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> getFavoritePlans(Long memberId) {
        List<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId);

        return favorites.stream()
                        .map(Favorite::getPlan)
                        .filter(plan ->
                                plan.getVisibility() == Plan.Visibility.PUBLIC ||
                                plan.getMember().getId().equals(memberId)
                        )
                        .map(PlanResponse::fromEntity)
                        .toList();
    }
}
