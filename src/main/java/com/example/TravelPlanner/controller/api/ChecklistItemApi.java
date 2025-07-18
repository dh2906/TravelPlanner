package com.example.TravelPlanner.controller.api;

import com.example.TravelPlanner.dto.request.ChecklistItemRequest;
import com.example.TravelPlanner.dto.response.ChecklistItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/checklist")
@Tag(name = "체크리스트 API", description = "체크리스트 항목 CRUD 및 상태 토글 API")
public interface ChecklistItemApi {
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "현재 로그인한 사용자의 체크리스트 항목들을 조회한다.")
    ResponseEntity<List<ChecklistItemResponse>> getChecklistItems(Long memberId);

    @ApiResponses({
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "현재 로그인한 사용자의 모든 체크리스트 항목을 삭제한다.")
    ResponseEntity<Void> deleteAllChecklistItems(Long memberId);

    @ApiResponses({
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "새로운 체크리스트 항목을 생성한다.")
    ResponseEntity<ChecklistItemResponse> createChecklistItem(
        Long memberId,
        ChecklistItemRequest request
    );

    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "기존 체크리스트 항목을 수정한다.")
    ResponseEntity<ChecklistItemResponse> updateChecklistItem(
        Long memberId,
        Long itemId,
        ChecklistItemRequest request
    );

    @ApiResponses({
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "단일 체크리스트 항목을 삭제한다.")
    ResponseEntity<Void> deleteChecklistItem(
        Long memberId,
        Long itemId
    );

    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "체크리스트 항목의 체크 상태를 반전시킨다.")
    ResponseEntity<String> toggleChecklistItemChecked(
        Long memberId,
        Long itemId
    );

    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "모든 체크리스트 항목의 체크 상태를 해제한다.")
    ResponseEntity<String> clearAllCheckedItems(Long memberId);
}
