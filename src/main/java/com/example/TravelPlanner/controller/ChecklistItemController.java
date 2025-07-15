package com.example.TravelPlanner.controller;

import com.example.TravelPlanner.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.global.annotation.LoginMember;
import com.example.TravelPlanner.service.ChecklistItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChecklistItemController {
    private final ChecklistItemService checklistItemService;

    @GetMapping("/me")
    public ResponseEntity<List<ChecklistItemResponse>> getMyChecklistItems(
            @LoginMember Long memberId
    ) {
        List<ChecklistItemResponse> response = checklistItemService.getMyChecklistItems(memberId);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/items")
    public ResponseEntity<Void> deleteMyChecklistItems(
            @LoginMember Long memberId
    ) {
        checklistItemService.deleteMyChecklistItems(memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/items")
    public ResponseEntity<ChecklistItemResponse> createChecklistItem(
            @LoginMember Long memberId,
            @RequestBody @Valid ChecklistItemRequest request
    ) {
        ChecklistItemResponse response = checklistItemService.createChecklistItem(memberId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ChecklistItemResponse> updateChecklistItem(
            @LoginMember Long memberId,
            @PathVariable Long id,
            @RequestBody @Valid ChecklistItemRequest request
    ) {
        ChecklistItemResponse response = checklistItemService.updateChecklistItem(memberId, id, request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteChecklistItem(
            @LoginMember Long memberId,
            @PathVariable Long id
    ) {
        checklistItemService.deleteChecklistItem(memberId, id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/items/{id}/toggle")
    public ResponseEntity<String> toggleChecklistItem(
            @LoginMember Long memberId,
            @PathVariable Long id
    ) {
        boolean isChecked = checklistItemService.toggleChecklistItem(memberId, id);

        String message = isChecked ? "항목이 체크되었습니다." : "항목이 체크 해제되었습니다.";

        return ResponseEntity
                .ok(message);
    }

    @PostMapping("/items/clear")
    public ResponseEntity<String> clearCheckedAllItems(
            @LoginMember Long memberId
    ) {
        checklistItemService.clearCheckedAllItems(memberId);

        return ResponseEntity
                .ok("모든 항목의 체크가 해제되었습니다.");
    }
}
