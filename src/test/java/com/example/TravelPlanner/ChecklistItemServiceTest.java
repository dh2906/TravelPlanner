package com.example.TravelPlanner;

import com.example.TravelPlanner.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.dto.response.ChecklistItemResponse;
import com.example.TravelPlanner.entity.ChecklistItem;
import com.example.TravelPlanner.entity.Member;
import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import com.example.TravelPlanner.repository.ChecklistItemRepository;
import com.example.TravelPlanner.repository.MemberRepository;
import com.example.TravelPlanner.service.ChecklistItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChecklistItemServiceTest {

    @Mock
    private ChecklistItemRepository checklistItemRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ChecklistItemService checklistItemService;

    @Test
    void 체크리스트_조회_성공() {
        Long memberId = 1L;
        ChecklistItem item = ChecklistItem.builder()
            .id(1L)
            .name("name")
            .description("item")
            .member(Member.builder().id(memberId).build())
            .build();

        given(checklistItemRepository.findAllByMemberId(memberId)).willReturn(List.of(item));

        List<ChecklistItemResponse> result = checklistItemService.getChecklistItems(memberId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).name()).isEqualTo("name");
        assertThat(result.get(0).description()).isEqualTo("item");
    }

    @Test
    void 체크리스트_추가_성공() {
        Long memberId = 1L;
        ChecklistItemRequest request = new ChecklistItemRequest("new name", "new item");
        Member member = Member.builder()
            .id(memberId)
            .build();

        ChecklistItem item = request.toEntity(member);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
        given(checklistItemRepository.save(any(ChecklistItem.class))).willReturn(item);

        ChecklistItemResponse response = checklistItemService.createChecklistItem(memberId, request);

        assertThat(response.name()).isEqualTo("new name");
        assertThat(response.description()).isEqualTo("new item");
        assertThat(response.isChecked()).isEqualTo(false);
    }

    @Test
    void 체크리스트_추가_실패_멤버없음() {
        ChecklistItemRequest request = new ChecklistItemRequest("new name", "new item");
        Long memberId = 1L;

        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> checklistItemService.createChecklistItem(memberId, request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    void 체크리스트_수정_성공() {
        Long memberId = 1L;
        Long itemId = 2L;
        ChecklistItemRequest request = new ChecklistItemRequest("updated name", "updated item");

        Member member = Member.builder()
            .id(memberId)
            .build();

        ChecklistItem item = ChecklistItem.builder()
            .id(itemId)
            .name("old name")
            .description("old item")
            .member(member)
            .build();

        given(checklistItemRepository.findById(itemId)).willReturn(Optional.of(item));

        ChecklistItemResponse response = checklistItemService.updateChecklistItem(memberId, itemId, request);

        assertThat(response.name()).isEqualTo("updated name");
        assertThat(response.description()).isEqualTo("updated item");
    }

    @Test
    void 체크리스트_수정_실패_권한없음() {
        Long memberId = 1L;
        Long itemId = 2L;
        ChecklistItemRequest request = new ChecklistItemRequest("update fail name", "update fail item");

        Member other = Member.builder()
            .id(999L)
            .build();

        ChecklistItem item = ChecklistItem.builder()
            .id(itemId)
            .name("name")
            .description("item")
            .member(other)
            .build();

        given(checklistItemRepository.findById(itemId)).willReturn(Optional.of(item));

        assertThatThrownBy(() -> checklistItemService.updateChecklistItem(memberId, itemId, request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.ACCESS_DENIED.getMessage());
    }

    @Test
    void 체크리스트_삭제_성공() {
        Long memberId = 1L;
        Long itemId = 2L;

        ChecklistItem item = ChecklistItem.builder()
            .id(itemId)
            .member(Member.builder().id(memberId).build())
            .build();

        given(checklistItemRepository.findById(itemId)).willReturn(Optional.of(item));

        checklistItemService.deleteChecklistItem(memberId, itemId);

        verify(checklistItemRepository).deleteById(itemId);
    }

    @Test
    void 체크리스트_삭제_실패_다른_유저() {
        Long memberId = 1L;
        Long itemId = 2L;

        ChecklistItem item = ChecklistItem.builder()
            .id(itemId)
            .member(Member.builder().id(999L).build())
            .build();

        given(checklistItemRepository.findById(itemId)).willReturn(Optional.of(item));

        assertThatThrownBy(() -> checklistItemService.deleteChecklistItem(memberId, itemId))
            .isInstanceOf(CustomException.class)
            .hasMessage(ExceptionCode.ACCESS_DENIED.getMessage());
    }

    @Test
    void 체크리스트_체크_상태_토글() {
        Long memberId = 1L;
        Long itemId = 10L;

        ChecklistItem item = ChecklistItem.builder()
            .id(itemId)
            .member(Member.builder().id(memberId).build())
            .build();

        given(checklistItemRepository.findById(itemId)).willReturn(Optional.of(item));

        boolean result = checklistItemService.toggleChecklistItemChecked(memberId, itemId);
        assertThat(result).isTrue();

        result = checklistItemService.toggleChecklistItemChecked(memberId, itemId);
        assertThat(result).isFalse();
    }
}
