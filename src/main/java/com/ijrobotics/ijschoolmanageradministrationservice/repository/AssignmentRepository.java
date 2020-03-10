package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;

import feign.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the Assignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudentIdAndClassGroupId(@Param("studentId") Long studentId,@Param("classGroupId") Long classGroupId);
    List<Assignment> findByStudentIdAndClassGroupIdAndDueDateBetween(@Param("studentId") Long studentId,@Param("classGroupId") Long classGroupId, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
    List<Assignment> findByClassGroupId(@Param("classGroupId") Long classGroupId);
    List<Assignment> findByClassGroupIdAndDueDateBetween(@Param("classGroupId") Long classGroupId, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
    List<Assignment> findByStudentId(@Param("studentId") Long studentId);
    List<Assignment> findByStudentIdAndDueDateBetween(@Param("studentId") Long studentId, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
}
