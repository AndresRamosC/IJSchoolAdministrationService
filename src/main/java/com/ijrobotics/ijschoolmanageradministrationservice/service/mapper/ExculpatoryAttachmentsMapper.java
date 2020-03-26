package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttachmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExculpatoryAttachments} and its DTO {@link ExculpatoryAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExculpatoryAttContentMapper.class, ExculpatoryMapper.class})
public interface ExculpatoryAttachmentsMapper extends EntityMapper<ExculpatoryAttachmentsDTO, ExculpatoryAttachments> {

    @Mapping(source = "exculpatoryAttContent.id", target = "exculpatoryAttContentId")
    @Mapping(source = "exculpatory.id", target = "exculpatoryId")
    ExculpatoryAttachmentsDTO toDto(ExculpatoryAttachments exculpatoryAttachments);

    @Mapping(source = "exculpatoryAttContentId", target = "exculpatoryAttContent")
    @Mapping(source = "exculpatoryId", target = "exculpatory")
    ExculpatoryAttachments toEntity(ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO);

    default ExculpatoryAttachments fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExculpatoryAttachments exculpatoryAttachments = new ExculpatoryAttachments();
        exculpatoryAttachments.setId(id);
        return exculpatoryAttachments;
    }
}
