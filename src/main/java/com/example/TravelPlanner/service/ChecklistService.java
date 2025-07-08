package com.example.TravelPlanner.service;

import com.example.TravelPlanner.controller.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.controller.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final ChecklistRepository checklistRepository;

    public List<ChecklistItemResponse> getMyChecklistItems(Member member) {
        return checklistRepository.findAllByMember(member)
                .stream()
                .map(ChecklistItemResponse::fromEntity)
                .toList();
    }

    public void deleteMyChecklistItems(Member member) {
        checklistRepository.deleteAllByMember(member);
    }

    public ChecklistItemResponse createChecklistItem(
            Member member,
            ChecklistItemRequest request
    ) {
        return ChecklistItemResponse.fromEntity(
                checklistRepository.save(
                        request.toEntity(member)
                )
        );
    }
}
