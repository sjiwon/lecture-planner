package com.sjiwon.lectureplanner.auth.controller.view;

import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class AuthController {
    @GetMapping
    public String init(@SessionAttribute(name = "principal", required = false) StudentPrincipal principal) {
        if (principal == null) {
            return "login";
        }
        return "authenticatedStudent";
    }
}
