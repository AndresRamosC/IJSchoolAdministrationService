package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.io.Serializable;

public class StudentAndPersonDto implements Serializable {
    private StudentDTO studentDTO;
    private  PersonDTO personDTO;

    public StudentAndPersonDto(StudentDTO studentDTO, PersonDTO personDTO) {
        this.studentDTO = studentDTO;
        this.personDTO = personDTO;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    @Override
    public String toString() {
        return "StudentAndPersonDTO{" +
            "studentDTO=" + studentDTO +
            ", personDTO=" + personDTO +
            '}';
    }
}
