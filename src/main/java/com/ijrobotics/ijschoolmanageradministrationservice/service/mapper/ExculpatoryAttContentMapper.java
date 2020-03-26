package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryAttContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExculpatoryAttContent} and its DTO {@link ExculpatoryAttContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExculpatoryAttContentMapper extends EntityMapper<ExculpatoryAttContentDTO, ExculpatoryAttContent> {


    @Mapping(target = "exculpatoryAttachments", ignore = true)
    ExculpatoryAttContent toEntity(ExculpatoryAttContentDTO exculpatoryAttContentDTO);

    default ExculpatoryAttContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExculpatoryAttContent exculpatoryAttContent = new ExculpatoryAttContent();
        exculpatoryAttContent.setId(id);
        return exculpatoryAttContent;
    }
}
