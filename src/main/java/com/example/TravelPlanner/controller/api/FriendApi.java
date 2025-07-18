package com.example.TravelPlanner.controller.api;

import com.example.TravelPlanner.dto.response.FriendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/friends")
@Tag(name = "친구 API", description = "친구 조회 및 삭제 API")
public interface FriendApi {
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "친구 목록을 조회한다.")
    ResponseEntity<List<FriendResponse>> getFriends(Long memberId);

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "특정 친구를 삭제한다.")
    ResponseEntity<String> deleteFriend(
        Long memberId,
        Long friendId
    );
}
