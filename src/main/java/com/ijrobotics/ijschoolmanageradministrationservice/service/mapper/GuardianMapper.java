package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guardian} and its DTO {@link GuardianDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface GuardianMapper extends EntityMapper<GuardianDTO, Guardian> {

    @Mapping(source = "person.id", target = "personId")
    GuardianDTO toDto(Guardian guardian);

    @Mapping(source = "personId", target = "person")
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "removeStudent", ignore = true)
    Guardian toEntity(GuardianDTO guardianDTO);

    default Guardian fromId(Long id) {
        if (id == null) {
            return null;
        }
        Guardian guardian = new Guardian();
        guardian.setId(id);
        return guardian;
    }
}
