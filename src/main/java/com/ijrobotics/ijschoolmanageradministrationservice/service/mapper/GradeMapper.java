package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GradeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grade} and its DTO {@link GradeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class, StudentMapper.class})
public interface GradeMapper extends EntityMapper<GradeDTO, Grade> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "student.id", target = "studentId")
    GradeDTO toDto(Grade grade);

    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "studentId", target = "student")
    Grade toEntity(GradeDTO gradeDTO);

    default Grade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grade grade = new Grade();
        grade.setId(id);
        return grade;
    }
}
