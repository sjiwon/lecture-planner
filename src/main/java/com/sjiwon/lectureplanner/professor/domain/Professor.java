package com.sjiwon.lectureplanner.professor.domain;

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
@Table(name = "professor")
public class Professor extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "RAW(16)")
    private UUID id;

    private String name;

    private Professor(String name) {
        this.name = name;
    }

    public static Professor createProfessor(String name) {
        return new Professor(name);
    }
}
