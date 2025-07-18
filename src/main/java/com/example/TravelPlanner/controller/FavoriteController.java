package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.api.FavoriteApi;
import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController implements FavoriteApi {
    private final FavoriteService favoriteService;

    @PatchMapping("/{planId}")
    public ResponseEntity<String> toggleFavorite(
        @LoginMember Long memberId,
        @PathVariable Long planId
    ) {
        boolean isFavorited = favoriteService.toggleFavorite(memberId, planId);

        String message = isFavorited ? "즐겨찾기 추가됨" : "즐겨찾기 해제됨";

        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getFavoritePlans(
        @LoginMember Long memberId
    ) {
        List<PlanResponse> response = favoriteService.getFavoritePlans(memberId);

        return ResponseEntity
            .ok(response);
    }
}
