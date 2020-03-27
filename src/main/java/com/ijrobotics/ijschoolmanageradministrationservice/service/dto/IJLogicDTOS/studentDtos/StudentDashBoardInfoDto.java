package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.studentDtos;



public class StudentDashBoardInfoDto {
    private StudentAndPersonDto studentPerson;

    public StudentDashBoardInfoDto( StudentAndPersonDto studentPerson) {
        this.studentPerson = studentPerson;
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
            ", studentPerson=" + studentPerson +
            '}';
    }
}
