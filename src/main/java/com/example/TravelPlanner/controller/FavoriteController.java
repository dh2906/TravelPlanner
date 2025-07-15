package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{planId}")
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
