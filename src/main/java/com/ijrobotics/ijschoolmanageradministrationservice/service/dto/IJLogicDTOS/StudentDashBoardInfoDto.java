package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;

public class StudentDashBoardInfoDto {
    private UserExtendDTO studentUserExtend;
    private StudentAndPersonDto studentPerson;

    public StudentDashBoardInfoDto(UserExtendDTO studentUserExtend, StudentAndPersonDto studentPerson) {
        this.studentUserExtend = studentUserExtend;
        this.studentPerson = studentPerson;
    }

    public UserExtendDTO getStudentUserExtend() {
        return studentUserExtend;
    }

    public void setStudentUserExtend(UserExtendDTO studentUserExtend) {
        this.studentUserExtend = studentUserExtend;
    }

    public StudentAndPersonDto getStudentPerson() {
        return studentPerson;
    }

    public void setStudentPerson(StudentAndPersonDto studentPerson) {
        this.studentPerson = studentPerson;
    }

    @Override
    public String toString() {
        return "StudentDashBoardInfoDto{" +
            "studentUserExtend=" + studentUserExtend +
            ", studentPerson=" + studentPerson +
            '}';
    }
}
