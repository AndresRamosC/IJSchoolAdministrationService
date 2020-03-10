package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;

import feign.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Assignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByClassGroupId(@Param("classGroupId") Long classGroupId);
    List<Assignment> findByClassGroupIdAndDueDateBetween(@Param("classGroupId") Long classGroupId, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
    Optional<Assignment> findByIdAndDueDateBetween(@Param("Id") Long Id, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
    Optional<Assignment> findByIdAndClassGroupId(@Param("Id") Long Id,@Param("classGroupId") Long classGroupId);
    Optional<Assignment> findByIdAndClassGroupIdAndDueDateBetween(@Param("Id") Long Id,@Param("classGroupId") Long classGroupId, ZonedDateTime CreationDate, ZonedDateTime endCreationDate);
}
