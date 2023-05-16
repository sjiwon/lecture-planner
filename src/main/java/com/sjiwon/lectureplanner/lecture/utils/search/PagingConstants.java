package com.sjiwon.lectureplanner.lecture.utils.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PagingConstants {
    int SLICE_PER_PAGE = 10;
    int RANGE_PER_PAGE = 5;

    static Pageable getPageRequest(int page) {
        return PageRequest.of(page - 1, SLICE_PER_PAGE);
    }
}
