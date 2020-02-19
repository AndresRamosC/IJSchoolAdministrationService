package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {


    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);

    default Subject fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(id);
        return subject;
    }
}
