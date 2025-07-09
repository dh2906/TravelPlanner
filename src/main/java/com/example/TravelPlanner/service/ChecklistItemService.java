package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.ChecklistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    private final ChecklistItemRepository checklistItemRepository;

    @Transactional(readOnly = true)
    public List<ChecklistItemResponse> getMyChecklistItems(Member member) {
        return checklistItemRepository.findAllByMember(member)
                .stream()
                .map(ChecklistItemResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteMyChecklistItems(Member member) {
        checklistItemRepository.deleteAllByMember(member);
    }

    @Transactional
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

    @Transactional
    public ChecklistItemResponse updateChecklistItem(
            Long id,
            ChecklistItemRequest request
    ) {
        ChecklistItem checklistItem = checklistItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.CHECKLIST_ITEM_NOT_FOUND));

        checklistItem.updateInfo(request);

        return ChecklistItemResponse.fromEntity(checklistItem);
    }

    @Transactional
    public void deleteChecklistItem(Long id) {
        checklistItemRepository.deleteById(id);
    }

    @Transactional
    public boolean toggleChecklistItem(Long id) {
        ChecklistItem checklistItem = checklistItemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionCode.CHECKLIST_ITEM_NOT_FOUND));

        checklistItem.updateChecked(!checklistItem.isChecked());

        return checklistItem.isChecked();
    }

    @Transactional
    public void clearCheckedAllItems(Member member) {
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByMember(member);

        for (ChecklistItem item : checklistItems) {
            item.updateChecked(false);
        }
    }
}
