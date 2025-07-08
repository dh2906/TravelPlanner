package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.controller.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.ChecklistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    private final ChecklistItemRepository checklistItemRepository;

    public List<ChecklistItemResponse> getMyChecklistItems(Member member) {
        return checklistItemRepository.findAllByMember(member)
                .stream()
                .map(ChecklistItemResponse::fromEntity)
                .toList();
    }

    public void deleteMyChecklistItems(Member member) {
        checklistItemRepository.deleteAllByMember(member);
    }

    public ChecklistItemResponse createChecklistItem(
            Member member,
            ChecklistItemRequest request
    ) {
        return ChecklistItemResponse.fromEntity(
                checklistItemRepository.save(
                        request.toEntity(member)
                )
        );
    }

    public ChecklistItemResponse updateChecklistItem(
            Long id,
            ChecklistItemRequest request
    ) {
        ChecklistItem checklistItem = checklistItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.CHECKLIST_ITEM_NOT_FOUND));

        checklistItem.updateInfo(request);

        return ChecklistItemResponse.fromEntity(checklistItem);
    }
}
