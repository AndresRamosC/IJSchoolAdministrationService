package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserExtendMapper.class, ContactMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(source = "userExtend.id", target = "userExtendId")
    PersonDTO toDto(Person person);

    @Mapping(source = "userExtendId", target = "userExtend")
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
