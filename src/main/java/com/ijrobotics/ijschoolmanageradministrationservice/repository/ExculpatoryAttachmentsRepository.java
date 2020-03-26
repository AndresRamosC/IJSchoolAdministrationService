package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ExculpatoryAttachments;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExculpatoryAttachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExculpatoryAttachmentsRepository extends JpaRepository<ExculpatoryAttachments, Long> {

}
