package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ExculpatoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exculpatory} and its DTO {@link ExculpatoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {GuardianMapper.class, StudentMapper.class})
public interface ExculpatoryMapper extends EntityMapper<ExculpatoryDTO, Exculpatory> {

    @Mapping(source = "guardian.id", target = "guardianId")
    @Mapping(source = "student.id", target = "studentId")
    ExculpatoryDTO toDto(Exculpatory exculpatory);

    @Mapping(source = "guardianId", target = "guardian")
    @Mapping(source = "studentId", target = "student")
    Exculpatory toEntity(ExculpatoryDTO exculpatoryDTO);

    default Exculpatory fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exculpatory exculpatory = new Exculpatory();
        exculpatory.setId(id);
        return exculpatory;
    }
}
