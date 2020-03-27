package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

import java.io.Serializable;
import java.util.Objects;

public class NewGuardianDto implements Serializable {
    private ContactDTO contactDTO;
    private PersonDTO personDTO;
    private GuardianDTO guardianDTO;

    public NewGuardianDto() {
        super();
    }

    public NewGuardianDto(ContactDTO contactDTO, PersonDTO personDTO, GuardianDTO guardianDTO) {
        this.contactDTO = contactDTO;
        this.personDTO = personDTO;
        this.guardianDTO = guardianDTO;
    }

    public ContactDTO getContactDTO() {
        return contactDTO;
    }

    public void setContactDTO(ContactDTO contactDTO) {
        this.contactDTO = contactDTO;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    public GuardianDTO getGuardianDTO() {
        return guardianDTO;
    }

    public void setGuardianDTO(GuardianDTO guardianDTO) {
        this.guardianDTO = guardianDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewGuardianDto that = (NewGuardianDto) o;
        return Objects.equals(contactDTO, that.contactDTO) &&
            Objects.equals(personDTO, that.personDTO) &&
            Objects.equals(guardianDTO, that.guardianDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDTO, personDTO, guardianDTO);
    }

    @Override
    public String toString() {
        return "NewGuardianDto{" +
            "contactDTO=" + contactDTO +
            ", personDTO=" + personDTO +
            ", guardianDTO=" + guardianDTO +
            '}';
    }
}
