package com.example.TravelPlanner.service;

import com.example.TravelPlanner.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.ChecklistItemRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    private final ChecklistItemRepository checklistItemRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<ChecklistItemResponse> getChecklistItems(
        Long memberId
    ) {
        return checklistItemRepository.findAllByMemberId(memberId)
            .stream()
            .map(ChecklistItemResponse::fromEntity)
            .toList();
    }

    @Transactional
    public void deleteAllChecklistItems(
        Long memberId
    ) {
        checklistItemRepository.deleteAllByMemberId(memberId);
    }

    @Transactional
    public ChecklistItemResponse createChecklistItem(
        Long memberId,
        ChecklistItemRequest request
    ) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        return ChecklistItemResponse.fromEntity(
            checklistItemRepository.save(
                request.toEntity(member)
            )
        );
    }

    @Transactional
    public ChecklistItemResponse updateChecklistItem(
        Long memberId,
        Long itemId,
        ChecklistItemRequest request
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(itemId);
        validateOwnership(memberId, checklistItem);

        checklistItem.updateInfo(request);

        return ChecklistItemResponse.fromEntity(checklistItem);
    }

    @Transactional
    public void deleteChecklistItem(
        Long memberId,
        Long itemId
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(itemId);
        validateOwnership(memberId, checklistItem);

        checklistItemRepository.deleteById(itemId);
    }

    @Transactional
    public boolean toggleChecklistItemChecked(
        Long memberId,
        Long itemId
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(itemId);
        validateOwnership(memberId, checklistItem);

        checklistItem.updateChecked(!checklistItem.isChecked());

        return checklistItem.isChecked();
    }

    @Transactional
    public void clearAllCheckedItems(
        Long memberId
    ) {
        checklistItemRepository.clearCheckedAllItems(memberId);
    }

    private ChecklistItem findChecklistItemOrThrow(
        Long itemId
    ) {
        return checklistItemRepository.findById(itemId)
            .orElseThrow(() -> new CustomException(ExceptionCode.CHECKLIST_ITEM_NOT_FOUND));
    }

    private void validateOwnership(
        Long memberId,
        ChecklistItem checklistItem
    ) {
        if (!checklistItem.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }
    }
}
