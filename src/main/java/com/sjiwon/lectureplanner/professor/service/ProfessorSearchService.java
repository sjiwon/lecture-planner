package com.sjiwon.lectureplanner.professor.service;

import com.sjiwon.lectureplanner.professor.domain.Professor;
import com.sjiwon.lectureplanner.professor.domain.ProfessorRepository;
import com.sjiwon.lectureplanner.professor.service.dto.response.ProfessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorSearchService {
    private final ProfessorRepository professorRepository;

    public List<ProfessorResponse> findAllProfessor() {
        List<Professor> professors = professorRepository.findAll();

        return professors.stream()
                .map(ProfessorResponse::new)
                .toList();
    }
}
