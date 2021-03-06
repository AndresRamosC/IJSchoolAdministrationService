package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Person entity.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "select distinct person from Person person left join fetch person.contacts",
        countQuery = "select count(distinct person) from Person person")
    Page<Person> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct person from Person person left join fetch person.contacts")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.contacts where person.id =:id")
    Optional<Person> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Person> findByKeycloakUserId(@Param("id") String id);

}
