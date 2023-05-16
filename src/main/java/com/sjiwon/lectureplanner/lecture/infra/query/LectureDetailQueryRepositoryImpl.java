package com.sjiwon.lectureplanner.lecture.infra.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sjiwon.lectureplanner.lecture.domain.Lecture;
import com.sjiwon.lectureplanner.lecture.utils.search.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.sjiwon.lectureplanner.lecture.domain.QLecture.lecture;
import static com.sjiwon.lectureplanner.professor.domain.QProfessor.professor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureDetailQueryRepositoryImpl implements LectureDetailQueryRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Lecture> findLectureByCondition(SearchCondition condition, Pageable pageable) {
        List<Lecture> lectures = query
                .select(lecture)
                .from(lecture)
                .innerJoin(lecture.professor, professor).fetchJoin()
                .where(
                        lectureNameEq(condition.lectureName()),
                        lectureStartPeriodEq(condition.startPeriod()),
                        lectureEndPeriodEq(condition.endPeriod()),
                        professorIdEq(condition.professorId())
                )
                .orderBy(
                        lecture.possibleGrade.asc(),
                        lecture.name.asc(),
                        lecture.dayOfWeek.asc(),
                        lecture.startPeriod.asc(),
                        lecture.credit.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query
                .select(lecture.count())
                .from(lecture)
                .innerJoin(lecture.professor, professor)
                .where(
                        lectureNameEq(condition.lectureName()),
                        lectureStartPeriodEq(condition.startPeriod()),
                        lectureEndPeriodEq(condition.endPeriod()),
                        professorIdEq(condition.professorId())
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(lectures, pageable, () -> count);
    }

    private BooleanExpression lectureNameEq(String lectureName) {
        return (lectureName != null) ? lecture.name.eq(lectureName) : null;
    }

    private BooleanExpression lectureStartPeriodEq(Integer startPeriod) {
        return (startPeriod != null) ? lecture.startPeriod.eq(startPeriod) : null;
    }

    private BooleanExpression lectureEndPeriodEq(Integer endPeriod) {
        return (endPeriod != null) ? lecture.endPeriod.eq(endPeriod) : null;
    }


    private BooleanExpression professorIdEq(UUID professorId) {
        return (professorId != null) ? professor.id.eq(professorId) : null;
    }
}
