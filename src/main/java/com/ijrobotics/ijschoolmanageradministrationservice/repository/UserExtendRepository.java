package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.UserExtend;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the UserExtend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtendRepository extends JpaRepository<UserExtend, Long> {
    Optional<UserExtend> findByKeycloakUserId(String keycloakUserId);
}
