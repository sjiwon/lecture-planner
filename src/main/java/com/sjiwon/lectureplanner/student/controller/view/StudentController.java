package com.sjiwon.lectureplanner.student.controller.view;

import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import com.sjiwon.lectureplanner.lecture.service.LectureSearchService;
import com.sjiwon.lectureplanner.lecture.service.dto.response.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final LectureSearchService lectureSearchService;

    @GetMapping("/my")
    public String myPage(@SessionAttribute(name = "principal", required = false) StudentPrincipal principal,
                         Model model) {
        if (principal == null) {
            return "login";
        }

        LectureResponse response = lectureSearchService.findLectureByStudentId(principal.id());
        model.addAttribute("lectureResponse", response);
        return "mySchedule";
    }
}
