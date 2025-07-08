package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.controller.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checklistService;

    @GetMapping
    public ResponseEntity<List<ChecklistItemResponse>> getMyChecklistItems(
            @LoginMember Member member
    ) {
        List<ChecklistItemResponse> response = checklistService.getMyChecklistItems(member);

        return ResponseEntity
                .ok(response);
    }
}
