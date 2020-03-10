package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Attachments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentsRepository extends JpaRepository<Attachments, Long> {
 @EntityGraph(attributePaths = "attachmentsContent")
    Optional<Attachments> findOneById(Long id);
    List<Attachments> findByAssignmentId(Long id);

}
