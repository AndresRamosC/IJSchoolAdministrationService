package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExculpatoryAttContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExculpatoryAttContentRepository extends JpaRepository<ExculpatoryAttContent, Long> {

}
