package com.sjiwon.lectureplanner.professor.service.dto.response;

import com.sjiwon.lectureplanner.professor.domain.Professor;

import java.util.UUID;

public record ProfessorResponse(
        UUID id,
        String name
) {
    public ProfessorResponse(Professor professor) {
        this(
                professor.getId(),
                professor.getName()
        );
    }
}
