package com.example.TravelPlanner.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequest(
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이내여야 합니다.")
        String password,

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(min = 2, max = 8, message = "이름은 2자 이상 8자 이내여야 합니다.")
        String name
) {
}
