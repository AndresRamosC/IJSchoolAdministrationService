package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.AttachmentsContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentsContent} and its DTO {@link AttachmentsContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentsContentMapper extends EntityMapper<AttachmentsContentDTO, AttachmentsContent> {


    @Mapping(target = "attachments", ignore = true)
    AttachmentsContent toEntity(AttachmentsContentDTO attachmentsContentDTO);

    default AttachmentsContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttachmentsContent attachmentsContent = new AttachmentsContent();
        attachmentsContent.setId(id);
        return attachmentsContent;
    }
}
