package com.sjiwon.lectureplanner.auth.service.dto.response;

import java.util.UUID;

public record StudentPrincipal(
        UUID id,
        String name,
        int grade
) {
}
