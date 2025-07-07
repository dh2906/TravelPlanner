package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/{planId}")
    public ResponseEntity<String> toggleFavorite(
            @LoginMember Member member,
            @PathVariable Long planId
    ) {
        boolean isFavorited = favoriteService.toggleFavorite(member.getId(), planId);

        String message = isFavorited ? "즐겨찾기 추가됨" : "즐겨찾기 해제됨";
        
        return ResponseEntity.ok(message);
    }
}
