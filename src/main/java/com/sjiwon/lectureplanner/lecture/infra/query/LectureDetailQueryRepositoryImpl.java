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

import static com.sjiwon.lectureplanner.enroll.domain.QEnroll.enroll;
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
                        lectureNameContains(condition.lectureName()),
                        lectureStartPeriodEq(condition.startPeriod()),
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
                        lectureNameContains(condition.lectureName()),
                        lectureStartPeriodEq(condition.startPeriod()),
                        professorIdEq(condition.professorId())
                )
                .fetchOne();

        return PageableExecutionUtils.getPage(lectures, pageable, () -> count);
    }

    @Override
    public List<Lecture> findByStudentId(UUID studentId) {
        return query
                .select(lecture)
                .from(enroll)
                .innerJoin(lecture).on(lecture.id.eq(enroll.lectureId))
                .innerJoin(lecture.professor).fetchJoin()
                .where(enroll.studentId.eq(studentId))
                .orderBy(
                        lecture.dayOfWeek.asc(),
                        lecture.startPeriod.asc(),
                        lecture.possibleGrade.asc()
                )
                .fetch();
    }

    private BooleanExpression lectureNameContains(String lectureName) {
        return (lectureName != null) ? lecture.name.contains(lectureName) : null;
    }

    private BooleanExpression lectureStartPeriodEq(Integer startPeriod) {
        return (startPeriod != null) ? lecture.startPeriod.eq(startPeriod) : null;
    }

    private BooleanExpression professorIdEq(UUID professorId) {
        return (professorId != null) ? professor.id.eq(professorId) : null;
    }
}
