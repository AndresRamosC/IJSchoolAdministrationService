package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    @Mapping(source = "employee.id", target = "employeeId")
    TeacherDTO toDto(Teacher teacher);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);

    default Teacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
