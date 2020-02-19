package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Guardian;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Guardian entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {

}
