package com.sjiwon.lectureplanner.lecture.domain;

import com.sjiwon.lectureplanner.professor.domain.Professor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String name;

    private int credit;

    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    private int startPeriod;

    private int endPeriod;

    private int possibleGrade; // 0 = 전체 학년 수강 가능 Lecture

    private int maxStudent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
    private Professor professor;

    private Lecture(Professor professor, String name, int credit, DayOfWeek dayOfWeek,
                    int startPeriod, int endPeriod, int possibleGrade, int maxStudent) {
        this.professor = professor;
        this.name = name;
        this.credit = credit;
        this.dayOfWeek = dayOfWeek;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.possibleGrade = possibleGrade;
        this.maxStudent = maxStudent;
    }

    public static Lecture createLecture(Professor professor, String name, int credit, DayOfWeek dayOfWeek,
                                        int startPeriod, int endPeriod, int possibleGrade, int maxStudent) {
        return new Lecture(professor, name, credit, dayOfWeek, startPeriod, endPeriod, possibleGrade, maxStudent);
    }
}
