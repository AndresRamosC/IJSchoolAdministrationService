package com.ijrobotics.ijschoolmanageradministrationservice.repository;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select distinct student from Student student left join fetch student.guardians left join fetch student.classGroups",
        countQuery = "select count(distinct student) from Student student")
    Page<Student> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct student from Student student left join fetch student.guardians left join fetch student.classGroups")
    List<Student> findAllWithEagerRelationships();

//    @Query("select distinct student from Student student left join fetch student.guardians left join fetch student.classGroups")
    List<Student> findByGuardiansId(long guardianId);

    @Query("select student from Student student left join fetch student.guardians left join fetch student.classGroups where student.id =:id")
    Optional<Student> findOneWithEagerRelationships(@Param("id") Long id);

}
