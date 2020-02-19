package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {

}
