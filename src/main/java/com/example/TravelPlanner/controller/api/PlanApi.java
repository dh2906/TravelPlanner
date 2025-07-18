package com.example.TravelPlanner.controller.api;

import com.example.TravelPlanner.dto.request.PlanDetailBulkUpdateRequest;
import com.example.TravelPlanner.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.dto.request.PlanRequest;
import com.example.TravelPlanner.dto.response.PlanDetailResponse;
import com.example.TravelPlanner.dto.response.PlanResponse;
import com.example.TravelPlanner.dto.response.PlanWithDetailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/plans")
@Tag(name = "일정 API", description = "일정 및 상세 일정 CRUD API")
public interface PlanApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정을 생성한다.")
    ResponseEntity<PlanResponse> createPlan(
        Long memberId,
        PlanRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "현재 로그인한 회원의 일정 목록을 조회한다.")
    ResponseEntity<List<PlanResponse>> getMyPlans(Long memberId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200")
        }
    )
    @Operation(summary = "공개된 일정 목록을 조회한다.")
    ResponseEntity<List<PlanResponse>> getPublicPlans();

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정의 상세 정보를 조회한다.")
    ResponseEntity<PlanWithDetailsResponse> getPlanDetail(Long planId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정에 상세 일정을 생성한다.")
    ResponseEntity<PlanDetailResponse> createPlanDetail(
        Long planId,
        PlanDetailRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정에 상세 일정 여러 개를 일괄 생성한다.")
    ResponseEntity<List<PlanDetailResponse>> createPlanDetails(
        Long planId,
        List<PlanDetailRequest> request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정 정보를 수정한다.")
    ResponseEntity<PlanResponse> updatePlan(
        Long planId,
        PlanRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정을 삭제한다.")
    ResponseEntity<Void> deletePlan(Long planId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "플랜의 상세 일정을 수정한다.")
    ResponseEntity<PlanDetailResponse> updatePlanDetail(
        Long planId,
        Long detailId,
        PlanDetailRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정의 상세 일정 여러 개를 일괄 수정한다.")
    ResponseEntity<List<PlanDetailResponse>> updatePlanDetails(
        Long planId,
        List<PlanDetailBulkUpdateRequest> request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정의 상세 일정을 삭제한다.")
    ResponseEntity<Void> deletePlanDetail(
        Long planId,
        Long detailId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "일정 공유 URL을 조회한다.")
    ResponseEntity<String> getPlanSharePath(Long planId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "공유된 일정 정보를 조회한다.")
    ResponseEntity<PlanWithDetailsResponse> getSharedPlan(String sharePath);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200")
        }
    )
    @Operation(summary = "일정을 검색한다.")
    ResponseEntity<List<PlanResponse>> searchPlans(String keyword);
}
