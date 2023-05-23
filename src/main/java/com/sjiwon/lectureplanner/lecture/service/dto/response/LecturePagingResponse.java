package com.sjiwon.lectureplanner.lecture.service.dto.response;

import com.sjiwon.lectureplanner.lecture.utils.search.Pagination;

import java.util.List;

public record LecturePagingResponse(
        List<LectureInformation> result,
        Pagination pagination
) {
}
