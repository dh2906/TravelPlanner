package com.example.TravelPlanner.dto.request;

import com.example.TravelPlanner.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
    @Schema(description = "회원 이메일", example = "user@example.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$", message = "유효하지 않은 이메일 형식입니다.") @Size(max = 50)
    String email,

    @Schema(description = "회원 비밀번호", example = "password1234")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이내여야 합니다.")
    String password,

    @Schema(description = "회원 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 8, message = "이름은 2자 이상 8자 이내여야 합니다.")
    String name
) {
    public Member toEntity(
        String encodedPassword
    ) {
        return Member.builder()
            .email(email)
            .password(encodedPassword)
            .name(name)
            .role(Member.Role.USER)
            .build();
    }
}
