package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Exculpatory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Exculpatory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExculpatoryRepository extends JpaRepository<Exculpatory, Long> {

}
