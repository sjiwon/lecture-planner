package com.sjiwon.lectureplanner.professor.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Professor(String name) {
        this.name = name;
    }

    public static Professor createProfessor(String name) {
        return new Professor(name);
    }
}
