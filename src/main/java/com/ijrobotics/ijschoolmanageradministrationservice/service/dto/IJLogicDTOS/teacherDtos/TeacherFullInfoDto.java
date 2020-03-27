package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.teacherDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.EmployeeDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.PersonDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.TeacherDTO;

import java.io.Serializable;

public class TeacherFullInfoDto implements Serializable {
    private TeacherDTO teacherDto;
    private PersonDTO personDTO;
    private long subjectsAmount;
    private long groupsAmount;
    private EmployeeDTO employeeDTO;



    public TeacherDTO getTeacherDto() {
        return teacherDto;
    }

    public void setTeacherDto(TeacherDTO teacherDto) {
        this.teacherDto = teacherDto;
    }

    public PersonDTO getPersonDTO() {
        return personDTO;
    }

    public void setPersonDTO(PersonDTO personDTO) {
        this.personDTO = personDTO;
    }

    public long getSubjectsAmount() {
        return subjectsAmount;
    }

    public void setSubjectsAmount(long subjectsAmount) {
        this.subjectsAmount = subjectsAmount;
    }

    public long getGroupsAmount() {
        return groupsAmount;
    }

    public void setGroupsAmount(long groupsAmount) {
        this.groupsAmount = groupsAmount;
    }

    @Override
    public String toString() {
        return "TeacherFullInfoDto{" +
            "teacherDto=" + teacherDto +
            ", personDTO=" + personDTO +
            ", subjectsAmount=" + subjectsAmount +
            ", groupsAmount=" + groupsAmount +
            ", employeeDTO=" + employeeDTO +
            '}';
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public TeacherFullInfoDto(TeacherDTO teacherDto, EmployeeDTO employeeDTO,PersonDTO personDTO, long subjectsAmount, long groupsAmount) {
        this.teacherDto = teacherDto;
        this.personDTO = personDTO;
        this.subjectsAmount = subjectsAmount;
        this.groupsAmount = groupsAmount;
        this.employeeDTO = employeeDTO;
    }
}
