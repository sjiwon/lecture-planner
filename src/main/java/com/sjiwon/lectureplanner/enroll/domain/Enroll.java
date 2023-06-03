package com.sjiwon.lectureplanner.enroll.domain;

import com.sjiwon.lectureplanner.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.ParameterMode.IN;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "enroll")
@NamedStoredProcedureQuery(
        name = "Enroll.InsertEnroll",
        procedureName = "InsertEnroll",
        parameters = {
                @StoredProcedureParameter(mode = IN, name = "input_student_id", type = UUID.class),
                @StoredProcedureParameter(mode = IN, name = "input_lecture_id", type = UUID.class)
        }
)
public class Enroll extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private UUID studentId;

    private UUID lectureId;

    private Enroll(UUID studentId, UUID lectureId) {
        this.studentId = studentId;
        this.lectureId = lectureId;
    }

    public static Enroll enrollLecture(UUID studentId, UUID lectureId) {
        return new Enroll(studentId, lectureId);
    }
}
