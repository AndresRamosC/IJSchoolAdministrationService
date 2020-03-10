package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AssignmentAssignedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssignmentAssigned} and its DTO {@link AssignmentAssignedDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, AssignmentMapper.class})
public interface AssignmentAssignedMapper extends EntityMapper<AssignmentAssignedDTO, AssignmentAssigned> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "assignment.id", target = "assignmentId")
    AssignmentAssignedDTO toDto(AssignmentAssigned assignmentAssigned);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "assignmentId", target = "assignment")
    AssignmentAssigned toEntity(AssignmentAssignedDTO assignmentAssignedDTO);

    default AssignmentAssigned fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssignmentAssigned assignmentAssigned = new AssignmentAssigned();
        assignmentAssigned.setId(id);
        return assignmentAssigned;
    }
}
