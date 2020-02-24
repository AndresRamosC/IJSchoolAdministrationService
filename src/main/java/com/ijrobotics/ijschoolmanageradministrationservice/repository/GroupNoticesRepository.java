package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.GroupNotices;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GroupNotices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupNoticesRepository extends JpaRepository<GroupNotices, Long> {

}
