package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assignment} and its DTO {@link AssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class})
public interface AssignmentMapper extends EntityMapper<AssignmentDTO, Assignment> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    AssignmentDTO toDto(Assignment assignment);

    @Mapping(target = "assignmentAssigneds", ignore = true)
    @Mapping(target = "removeAssignmentAssigned", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "removeAttachments", ignore = true)
    @Mapping(source = "classGroupId", target = "classGroup")
    Assignment toEntity(AssignmentDTO assignmentDTO);

    default Assignment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assignment assignment = new Assignment();
        assignment.setId(id);
        return assignment;
    }
}
