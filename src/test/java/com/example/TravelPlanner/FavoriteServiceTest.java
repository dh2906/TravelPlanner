package com.example.TravelPlanner;

import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Favorite;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.entity.Plan;
import com.example.TravelPlanner.repository.FavoriteRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.repository.PlanRepository;
import com.example.TravelPlanner.service.FavoriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.TravelPlanner.entity.Plan.Visibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {
    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    void 즐겨찾기_없을때_추가() {
        Long memberId = 1L;
        Long planId = 2L;
        Member member = Member.builder().id(memberId).build();
        Plan plan = Plan.builder().id(planId).member(member).visibility(PUBLIC).build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(favoriteRepository.findByMemberIdAndPlanId(memberId, planId)).thenReturn(Optional.empty());

        boolean result = favoriteService.toggleFavorite(memberId, planId);

        assertThat(result).isTrue();
        verify(favoriteRepository).save(any(Favorite.class));
    }

    @Test
    void 즐겨찾기_있을때_삭제() {
        Long memberId = 1L;
        Long planId = 2L;
        Member member = Member.builder().id(memberId).build();
        Plan plan = Plan.builder().id(planId).member(member).visibility(PUBLIC).build();
        Favorite favorite = Favorite.builder().member(member).plan(plan).build();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(favoriteRepository.findByMemberIdAndPlanId(memberId, planId)).thenReturn(Optional.of(favorite));

        boolean result = favoriteService.toggleFavorite(memberId, planId);

        assertThat(result).isFalse();
        verify(favoriteRepository).delete(favorite);
    }

    @Test
    void 즐겨찾기_목록_조회_성공() {
        Long memberId = 1L;
        Member member = Member.builder().id(memberId).build();

        Plan publicPlan = Plan.builder()
            .id(10L)
            .visibility(Plan.Visibility.PUBLIC)
            .member(Member.builder().id(2L).build())
            .build();

        Plan ownPrivatePlan = Plan.builder()
            .id(20L)
            .visibility(Plan.Visibility.PRIVATE)
            .member(member)
            .build();

        Plan othersPrivatePlan = Plan.builder()
            .id(30L)
            .visibility(Plan.Visibility.PRIVATE)
            .member(Member.builder().id(3L).build())
            .build();

        List<Favorite> favorites = List.of(
            Favorite.builder().plan(publicPlan).build(),
            Favorite.builder().plan(ownPrivatePlan).build(),
            Favorite.builder().plan(othersPrivatePlan).build()
        );

        when(favoriteRepository.findAllByMemberIdWithPlan(memberId))
            .thenReturn(favorites);

        List<PlanResponse> result = favoriteService.getFavoritePlans(memberId);

        assertThat(result).hasSize(2);
        assertThat(result).extracting("id")
            .containsExactlyInAnyOrder(publicPlan.getId(), ownPrivatePlan.getId());
    }
}
