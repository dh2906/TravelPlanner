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

    @GetMapping
    public ResponseEntity<List<ChecklistItemResponse>> getChecklistItems(
            @LoginMember Long memberId
    ) {
        List<ChecklistItemResponse> response = checklistItemService.getChecklistItems(memberId);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllChecklistItems(
            @LoginMember Long memberId
    ) {
        checklistItemService.deleteAllChecklistItems(memberId);

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

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ChecklistItemResponse> updateChecklistItem(
            @LoginMember Long memberId,
            @PathVariable Long itemId,
            @RequestBody @Valid ChecklistItemRequest request
    ) {
        ChecklistItemResponse response = checklistItemService.updateChecklistItem(memberId, itemId, request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteChecklistItem(
            @LoginMember Long memberId,
            @PathVariable Long itemId
    ) {
        checklistItemService.deleteChecklistItem(memberId, itemId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/items/{itemId}/checked/toggle")
    public ResponseEntity<String> toggleChecklistItemChecked(
            @LoginMember Long memberId,
            @PathVariable Long itemId
    ) {
        boolean isChecked = checklistItemService.toggleChecklistItemChecked(memberId, itemId);

        String message = isChecked ? "항목이 체크되었습니다." : "항목이 체크 해제되었습니다.";

        return ResponseEntity
                .ok(message);
    }

    @DeleteMapping("/items/checked/clear")
    public ResponseEntity<String> clearAllCheckedItems(
            @LoginMember Long memberId
    ) {
        checklistItemService.clearAllCheckedItems(memberId);

        return ResponseEntity
                .ok("모든 항목의 체크가 해제되었습니다.");
    }
}
