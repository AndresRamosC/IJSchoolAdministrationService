package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import java.io.Serializable;
import java.util.Objects;

public class NewStudentDto implements Serializable {
    private ContactDTO contactDTO;
    private PersonDTO personDTO;
    private StudentDTO studentDTO;

    public NewStudentDto() {
        super();
    }

    public NewStudentDto(ContactDTO contactDTO, PersonDTO personDTO, StudentDTO studentDTO) {
        this.contactDTO = contactDTO;
        this.personDTO = personDTO;
        this.studentDTO = studentDTO;
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

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    @Override
    public String toString() {
        return "NewStudentDto{" +
            "contactDTO=" + contactDTO +
            ", personDTO=" + personDTO +
            ", studentDTO=" + studentDTO +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewStudentDto that = (NewStudentDto) o;
        return Objects.equals(contactDTO, that.contactDTO) &&
            Objects.equals(personDTO, that.personDTO) &&
            Objects.equals(studentDTO, that.studentDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDTO, personDTO, studentDTO);
    }
}
