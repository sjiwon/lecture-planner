package com.sjiwon.lectureplanner.enroll.controller.api;

import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import com.sjiwon.lectureplanner.enroll.service.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lectures/{lectureId}")
@RequiredArgsConstructor
public class EnrollApiController {
    private final EnrollService enrollService;

    @PostMapping
    public ResponseEntity<Void> enroll(@SessionAttribute(name = "principal", required = false) StudentPrincipal principal,
                                       @PathVariable UUID lectureId) {
        enrollService.enroll(lectureId, principal.id());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelEnroll(@SessionAttribute(name = "principal", required = false) StudentPrincipal principal,
                                             @PathVariable UUID lectureId) {
        enrollService.cancelEnroll(lectureId, principal.id());
        return ResponseEntity.noContent().build();
    }
}
