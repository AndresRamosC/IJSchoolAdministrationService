package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.AttachmentsContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttachmentsContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentsContentRepository extends JpaRepository<AttachmentsContent, Long> {

}
