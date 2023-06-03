package com.sjiwon.lectureplanner.auth.controller.view;

import com.sjiwon.lectureplanner.auth.service.dto.response.StudentPrincipal;
import com.sjiwon.lectureplanner.lecture.service.LectureSearchService;
import com.sjiwon.lectureplanner.lecture.service.dto.response.LecturePagingResponse;
import com.sjiwon.lectureplanner.lecture.utils.search.PagingConstants;
import com.sjiwon.lectureplanner.lecture.utils.search.SearchCondition;
import com.sjiwon.lectureplanner.professor.service.ProfessorSearchService;
import com.sjiwon.lectureplanner.professor.service.dto.response.ProfessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final ProfessorSearchService professorSearchService;
    private final LectureSearchService lectureSearchService;

    @GetMapping
    public String init(@SessionAttribute(name = "principal", required = false) StudentPrincipal principal,
                       @RequestParam(required = false) Integer grade,
                       @RequestParam(required = false) String lectureName,
                       @RequestParam(required = false) Integer startPeriod,
                       @RequestParam(required = false) UUID professorId,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       Model model) {
        if (principal == null) {
            return "login";
        }

        SearchCondition condition = new SearchCondition(grade, lectureName, startPeriod, professorId);
        Pageable pageable = PagingConstants.getPageRequest(page);

        applyLectureResponse(model, condition, pageable);
        applyProfessors(model);
        return "authenticatedStudent";
    }

    private void applyLectureResponse(Model model, SearchCondition condition, Pageable pageable) {
        LecturePagingResponse lecturePagingResponse = lectureSearchService.findLecture(condition, pageable);
        model.addAttribute("lectureResponse", lecturePagingResponse);
    }

    private void applyProfessors(Model model) {
        List<ProfessorResponse> professors = professorSearchService.findAllProfessor();
        model.addAttribute("professors", professors);
    }
}
