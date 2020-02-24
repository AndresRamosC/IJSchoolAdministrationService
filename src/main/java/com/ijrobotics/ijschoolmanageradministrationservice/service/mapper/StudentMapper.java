package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, GuardianMapper.class, ClassGroupMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "person.id", target = "personId")
    StudentDTO toDto(Student student);

    @Mapping(source = "personId", target = "person")
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "removeAttendance", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "removeGrade", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "removeAssignment", ignore = true)
    @Mapping(target = "exculpatories", ignore = true)
    @Mapping(target = "removeExculpatory", ignore = true)
    @Mapping(target = "removeGuardian", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
