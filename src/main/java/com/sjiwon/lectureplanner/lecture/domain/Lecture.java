package com.sjiwon.lectureplanner.lecture.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int credit;

    private int startPeriod;

    private int endPeriod;

    private int possibleGrade;

    private int maxStudent;

    private Lecture(String name, int credit, int startPeriod, int endPeriod, int possibleGrade, int maxStudent) {
        this.name = name;
        this.credit = credit;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.possibleGrade = possibleGrade;
        this.maxStudent = maxStudent;
    }

    public static Lecture createLecture(String name, int credit, int startPeriod, int endPeriod, int possibleGrade, int maxStudent) {
        return new Lecture(name, credit, startPeriod, endPeriod, possibleGrade, maxStudent);
    }
}
