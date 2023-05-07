package com.sjiwon.lectureplanner.enroll.domain;

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
@Table(name = "enroll")
public class Enroll extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private UUID lectureId;

    private UUID studentId;

    private Enroll(UUID lectureId, UUID studentId) {
        this.lectureId = lectureId;
        this.studentId = studentId;
    }

    public static Enroll enrollLecture(UUID lectureId, UUID studentId) {
        return new Enroll(lectureId, studentId);
    }
}
