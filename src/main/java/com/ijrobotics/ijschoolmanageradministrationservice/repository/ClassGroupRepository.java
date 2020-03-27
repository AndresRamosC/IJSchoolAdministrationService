package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ClassGroup entity.
 */
@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    List<ClassGroup> findByTeacherIdOrderBySubjectIdAsc(@Param("teacherId") Long id);

    List<ClassGroup> findByTeacherId(@Param("teacherId") Long id);

    List<ClassGroup> findByStudentsId(@Param("teacherId") Long id);

    long countBySubjectId(Long subjectId);

    @Query(value = "select distinct classGroup from ClassGroup classGroup left join fetch classGroup.students left join fetch classGroup.classSchedules",
        countQuery = "select count(distinct classGroup) from ClassGroup classGroup")
    Page<ClassGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct classGroup from ClassGroup classGroup left join fetch classGroup.students left join fetch classGroup.classSchedules")
    List<ClassGroup> findAllWithEagerRelationships();

    @Query("select classGroup from ClassGroup classGroup left join fetch classGroup.students left join fetch classGroup.classSchedules where classGroup.id =:id")
    Optional<ClassGroup> findOneWithEagerRelationships(@Param("id") Long id);
}
