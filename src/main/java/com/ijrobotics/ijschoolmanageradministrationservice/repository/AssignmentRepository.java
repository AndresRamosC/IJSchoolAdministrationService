package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Assignment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Assignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

}
