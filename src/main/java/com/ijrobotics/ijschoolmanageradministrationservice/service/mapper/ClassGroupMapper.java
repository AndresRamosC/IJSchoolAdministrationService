package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassGroup} and its DTO {@link ClassGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, SubjectMapper.class, TeacherMapper.class})
public interface ClassGroupMapper extends EntityMapper<ClassGroupDTO, ClassGroup> {

    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "teacher.id", target = "teacherId")
    ClassGroupDTO toDto(ClassGroup classGroup);

    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "removeAttendance", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "removeAssignment", ignore = true)
    @Mapping(target = "groupNotices", ignore = true)
    @Mapping(target = "removeGroupNotices", ignore = true)
    @Mapping(target = "removeStudent", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "teacherId", target = "teacher")
    ClassGroup toEntity(ClassGroupDTO classGroupDTO);

    default ClassGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(id);
        return classGroup;
    }
}
