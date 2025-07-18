package com.example.TravelPlanner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequest(
    @Schema(description = "변경할 비밀번호", example = "securePassword123")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이내여야 합니다.")
    String password,

    @Schema(description = "변경할 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 8, message = "이름은 2자 이상 8자 이내여야 합니다.")
    String name
) {
}
