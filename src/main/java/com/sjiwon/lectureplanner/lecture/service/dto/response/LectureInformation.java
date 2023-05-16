package com.sjiwon.lectureplanner.lecture.service.dto.response;

import com.sjiwon.lectureplanner.lecture.domain.DayOfWeek;
import com.sjiwon.lectureplanner.lecture.domain.Lecture;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sjiwon.lectureplanner.lecture.domain.DayOfWeek.*;

public record LectureInformation(
        UUID lectureId,
        UUID professorId,
        String professorName,
        String lectureName,
        int lectureCredit,
        String lecturePeriod,
        String possibleGrade
) {
    public LectureInformation(Lecture lecture) {
        this(
                lecture.getId(),
                lecture.getProfessor().getId(),
                lecture.getProfessor().getName(),
                lecture.getName(),
                lecture.getCredit(),
                generateTimeline(lecture.getDayOfWeek(), lecture.getStartPeriod(), lecture.getEndPeriod()),
                translatePossibleGrade(lecture.getPossibleGrade()));
    }

    private static String generateTimeline(DayOfWeek week, int startPeriod, int endPeriod) {
        return IntStream.rangeClosed(startPeriod, endPeriod)
                .mapToObj(String::valueOf).
                collect(Collectors.joining("", translateWeek(week), ""));
    }

    private static String translateWeek(DayOfWeek week) {
        return switch (week) {
            case MONDAY -> MONDAY.getValue();
            case TUESDAY -> TUESDAY.getValue();
            case WEDNESDAY -> WEDNESDAY.getValue();
            case THURSDAY -> THURSDAY.getValue();
            default -> FRIDAY.getValue();
        };
    }

    private static String translatePossibleGrade(int possibleGrade) {
        return (possibleGrade == 0) ? "공통" : possibleGrade + "학년";
    }
}
