package com.sjiwon.lectureplanner.student.domain;

import com.sjiwon.lectureplanner.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "student")
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int grade;

    private Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public static Student createStudent(String name, int grade) {
        return new Student(name, grade);
    }
}
