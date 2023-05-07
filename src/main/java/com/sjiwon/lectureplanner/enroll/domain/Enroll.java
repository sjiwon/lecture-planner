package com.sjiwon.lectureplanner.enroll.domain;

import com.sjiwon.lectureplanner.global.BaseEntity;
import com.sjiwon.lectureplanner.lecture.domain.Lecture;
import com.sjiwon.lectureplanner.student.domain.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enroll")
public class Enroll extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lecture_id", referencedColumnName = "id", nullable = false)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    private Enroll(Lecture lecture, Student student) {
        this.lecture = lecture;
        this.student = student;
    }

    public static Enroll enrollLecture(Lecture lecture, Student student) {
        return new Enroll(lecture, student);
    }
}
