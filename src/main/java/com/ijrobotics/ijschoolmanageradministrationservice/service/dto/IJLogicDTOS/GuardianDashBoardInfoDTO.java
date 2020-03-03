package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GuardianDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;

import java.io.Serializable;
import java.util.List;

public class GuardianDashBoardInfoDTO implements Serializable {
    private UserExtendDTO guardianUserExtend;
    private PersonDTO guardianPerson;
    private GuardianDTO guardianInfo;
    private List<StudentAndPersonDto> studentList;

    public GuardianDashBoardInfoDTO(UserExtendDTO guardianUserExtend, PersonDTO guardianPerson, GuardianDTO guardianInfo, List<StudentAndPersonDto> studentList) {
        this.guardianUserExtend = guardianUserExtend;
        this.guardianPerson = guardianPerson;
        this.guardianInfo = guardianInfo;
        this.studentList = studentList;
    }

    public UserExtendDTO getGuardianUserExtend() {
        return guardianUserExtend;
    }

    public void setGuardianUserExtend(UserExtendDTO guardianUserExtend) {
        this.guardianUserExtend = guardianUserExtend;
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
            "guardianUserExtend=" + guardianUserExtend +
            ", guardianPerson=" + guardianPerson +
            ", guardianInfo=" + guardianInfo +
            ", studentList=" + studentList +
            '}';
    }
}
