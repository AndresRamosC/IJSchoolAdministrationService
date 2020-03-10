package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.AssignmentAssigned;

import feign.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the AssignmentAssigned entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentAssignedRepository extends JpaRepository<AssignmentAssigned, Long> {
    List<AssignmentAssigned> findByStudentId(@Param("studentId") Long studentId);
}
