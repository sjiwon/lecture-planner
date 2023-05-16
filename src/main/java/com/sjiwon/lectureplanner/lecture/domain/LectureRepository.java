package com.sjiwon.lectureplanner.lecture.domain;

import com.sjiwon.lectureplanner.lecture.infra.query.LectureDetailQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LectureRepository extends JpaRepository<Lecture, UUID>, LectureDetailQueryRepository {
}
