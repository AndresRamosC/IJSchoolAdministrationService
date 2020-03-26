package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassSchedule} and its DTO {@link ClassScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassScheduleMapper extends EntityMapper<ClassScheduleDTO, ClassSchedule> {


    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    ClassSchedule toEntity(ClassScheduleDTO classScheduleDTO);

    default ClassSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setId(id);
        return classSchedule;
    }
}
