package com.sjiwon.lectureplanner.student.domain;

import com.sjiwon.lectureplanner.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "student")
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String loginId;

    private String password;

    private String name;

    private int grade;

    private Student(String loginId, String password, String name, int grade) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.grade = grade;
    }

    public static Student createStudent(String loginId, String password, String name, int grade) {
        return new Student(loginId, password, name, grade);
    }
}
