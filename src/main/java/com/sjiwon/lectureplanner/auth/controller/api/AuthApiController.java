package com.sjiwon.lectureplanner.auth.controller.api;

import com.sjiwon.lectureplanner.auth.controller.api.dto.request.LoginRequest;
import com.sjiwon.lectureplanner.auth.service.AuthService;
import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request,
                                      HttpServletRequest httpServletRequest) {
        StudentPrincipal principal = authService.login(request.loginId(), request.password());
        applyPrincipalToSession(httpServletRequest, principal);

        return ResponseEntity.noContent().build();
    }

    private void applyPrincipalToSession(HttpServletRequest request, StudentPrincipal principal) {
        HttpSession session = request.getSession(true);
        session.setAttribute("principal", principal);
    }
}
