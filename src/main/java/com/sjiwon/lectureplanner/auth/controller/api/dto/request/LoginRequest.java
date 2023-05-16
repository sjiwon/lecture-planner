package com.sjiwon.lectureplanner.auth.controller.api.dto.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "아이디를 입력하세요")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요")
        String password
) {
}
