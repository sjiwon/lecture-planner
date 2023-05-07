package com.sjiwon.lectureplanner.enroll.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollRepository extends JpaRepository<Enroll, UUID> {
}
