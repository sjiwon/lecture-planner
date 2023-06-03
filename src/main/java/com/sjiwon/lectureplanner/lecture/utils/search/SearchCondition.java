package com.sjiwon.lectureplanner.lecture.utils.search;

import java.util.UUID;

public record SearchCondition(
        // 1. 학년 기준 조회
        Integer grade,

        // 2. 강의명 기준 조회
        String lectureName,
        
        // 3. 강의 시간 기준 조회
        Integer startPeriod,

        // 4. 교수 기준 조회
        UUID professorId
) {
}
