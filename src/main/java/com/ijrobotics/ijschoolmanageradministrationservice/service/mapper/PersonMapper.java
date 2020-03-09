package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {


    @Mapping(target = "removeContact", ignore = true)
    @Mapping(target = "guardian", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "employee", ignore = true)
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
