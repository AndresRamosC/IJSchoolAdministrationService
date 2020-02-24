package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachments} and its DTO {@link AttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface AttachmentsMapper extends EntityMapper<AttachmentsDTO, Attachments> {

    @Mapping(source = "assignment.id", target = "assignmentId")
    AttachmentsDTO toDto(Attachments attachments);

    @Mapping(source = "assignmentId", target = "assignment")
    Attachments toEntity(AttachmentsDTO attachmentsDTO);

    default Attachments fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachments attachments = new Attachments();
        attachments.setId(id);
        return attachments;
    }
}
