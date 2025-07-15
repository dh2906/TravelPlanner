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
    public List<ChecklistItemResponse> getMyChecklistItems(Long memberId) {
        return checklistItemRepository.findAllByMemberId(memberId)
                .stream()
                .map(ChecklistItemResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteMyChecklistItems(Long memberId) {
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
            Long id,
            ChecklistItemRequest request
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(id);
        validateOwnership(memberId, checklistItem);

        checklistItem.updateInfo(request);

        return ChecklistItemResponse.fromEntity(checklistItem);
    }

    @Transactional
    public void deleteChecklistItem(
            Long memberId,
            Long id
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(id);
        validateOwnership(memberId, checklistItem);

        checklistItemRepository.deleteById(id);
    }

    @Transactional
    public boolean toggleChecklistItem(
            Long memberId,
            Long id
    ) {
        ChecklistItem checklistItem = findChecklistItemOrThrow(id);
        validateOwnership(memberId, checklistItem);

        checklistItem.updateChecked(!checklistItem.isChecked());

        return checklistItem.isChecked();
    }

    @Transactional
    public void clearCheckedAllItems(Long memberId) {
        checklistItemRepository.clearCheckedAllItems(memberId);
    }

    private ChecklistItem findChecklistItemOrThrow(Long id) {
        return checklistItemRepository.findById(id)
                                      .orElseThrow(() -> new CustomException(ExceptionCode.CHECKLIST_ITEM_NOT_FOUND));
    }

    private void validateOwnership(Long memberId,
                                   ChecklistItem checklistItem
    ) {
        if (!checklistItem.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }
    }
}
