package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.teacherDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ContactDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.EmployeeDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.TeacherDTO;

import java.io.Serializable;
import java.util.Objects;

public class NewTeacherDto implements Serializable {

    private ContactDTO contactDTO;
    private PersonDTO personDTO;
    private TeacherDTO teacherDTO;
    private EmployeeDTO employeeDTO;
    public NewTeacherDto(){
        super();
    }

    public NewTeacherDto(ContactDTO contactDTO, PersonDTO personDTO, TeacherDTO teacherDTO, EmployeeDTO employeeDTO) {
        this.contactDTO = contactDTO;
        this.personDTO = personDTO;
        this.teacherDTO = teacherDTO;
        this.employeeDTO = employeeDTO;
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

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    @Override
    public String toString() {
        return "NewTeacherDto{" +
            "contactDTO=" + contactDTO +
            ", personDTO=" + personDTO +
            ", teacherDTO=" + teacherDTO +
            ", employeeDTO=" + employeeDTO +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTeacherDto that = (NewTeacherDto) o;
        return Objects.equals(contactDTO, that.contactDTO) &&
            Objects.equals(personDTO, that.personDTO) &&
            Objects.equals(teacherDTO, that.teacherDTO) &&
            Objects.equals(employeeDTO, that.employeeDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactDTO, personDTO, teacherDTO, employeeDTO);
    }
}
