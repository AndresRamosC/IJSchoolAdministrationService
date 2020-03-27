package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.guardianDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos.StudentAndPersonDto;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;

import java.io.Serializable;
import java.util.List;

public class GuardianDashBoardInfoDTO implements Serializable {
    private PersonDTO guardianPerson;
    private GuardianDTO guardianInfo;
    private List<StudentAndPersonDto> studentList;

    public GuardianDashBoardInfoDTO(PersonDTO guardianPerson, GuardianDTO guardianInfo, List<StudentAndPersonDto> studentList) {

        this.guardianPerson = guardianPerson;
        this.guardianInfo = guardianInfo;
        this.studentList = studentList;
    }

    public PersonDTO getGuardianPerson() {
        return guardianPerson;
    }

    public void setGuardianPerson(PersonDTO guardianPerson) {
        this.guardianPerson = guardianPerson;
    }

    public GuardianDTO getGuardianInfo() {
        return guardianInfo;
    }

    public void setGuardianInfo(GuardianDTO guardianInfo) {
        this.guardianInfo = guardianInfo;
    }

    public List<StudentAndPersonDto> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentAndPersonDto> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "GuardianDashBoardInfoDto{" +
            ", guardianPerson=" + guardianPerson +
            ", guardianInfo=" + guardianInfo +
            ", studentList=" + studentList +
            '}';
    }
}
