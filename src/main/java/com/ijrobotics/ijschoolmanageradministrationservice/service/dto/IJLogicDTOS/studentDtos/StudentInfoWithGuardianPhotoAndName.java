package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos.GuardianPhotoAndName;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.StudentDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentInfoWithGuardianPhotoAndName implements Serializable {
    private List<GuardianPhotoAndName> guardianPhotoAndNameList=new ArrayList<>();
    private StudentDTO studentDTO;
    private PersonDTO personDTO;

    public StudentInfoWithGuardianPhotoAndName(List<GuardianPhotoAndName> guardianPhotoAndNameList, StudentDTO studentDTO, PersonDTO personDTO) {
        this.guardianPhotoAndNameList = guardianPhotoAndNameList;
        this.studentDTO = studentDTO;
        this.personDTO = personDTO;
    }

    public List<GuardianPhotoAndName> getGuardianPhotoAndNameList() {
        return guardianPhotoAndNameList;
    }

    public void setGuardianPhotoAndNameList(List<GuardianPhotoAndName> guardianPhotoAndNameList) {
        this.guardianPhotoAndNameList = guardianPhotoAndNameList;
    }

    public StudentDTO getStudentDTO() {
        return studentDTO;
    }

    @Override
    public String toString() {
        return "StudentInfoWithGuardianPhotoAndName{" +
            "guardianPhotoAndNameList=" + guardianPhotoAndNameList +
            ", studentDTO=" + studentDTO +
            ", personDTO=" + personDTO +
            '}';
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
}
