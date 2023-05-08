package com.sjiwon.lectureplanner.lecture.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek {
    MONDAY("월"), // 0
    TUESDAY("화"), // 1
    WEDNESDAY("수"), // 2
    THURSDAY("목"), // 3
    FRIDAY("금"), // 4
    ;

    private final String value;
}
