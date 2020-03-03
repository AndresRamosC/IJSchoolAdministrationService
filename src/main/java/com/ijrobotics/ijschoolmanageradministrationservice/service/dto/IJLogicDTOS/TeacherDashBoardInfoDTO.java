package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS;


import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.*;

import java.io.Serializable;
import java.util.List;

public class TeacherDashBoardInfoDTO implements Serializable {
    private UserExtendDTO teacherUserExtend;
    private PersonDTO teacherPerson;
    private TeacherDTO teacherInfo;
    private EmployeeDTO employeeDTO;
    private List<ClassGroupAndSubjectDto> classGroupDTOList;
    private List<SubjectAmountDto> amountOfGroups;

    public TeacherDashBoardInfoDTO(UserExtendDTO teacherUserExtend, PersonDTO teacherPerson, EmployeeDTO employeeDTO, TeacherDTO teacherInfo, List<ClassGroupAndSubjectDto> classGroupDTOList,List<SubjectAmountDto>  amountOfGroups) {
        this.teacherUserExtend = teacherUserExtend;
        this.teacherPerson = teacherPerson;
        this.teacherInfo = teacherInfo;
        this.employeeDTO = employeeDTO;
        this.classGroupDTOList=classGroupDTOList;
        this.amountOfGroups=amountOfGroups;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public UserExtendDTO getTeacherUserExtend() {
        return teacherUserExtend;
    }

    public void setTeacherUserExtend(UserExtendDTO teacherUserExtend) {
        this.teacherUserExtend = teacherUserExtend;
    }

    public PersonDTO getTeacherPerson() {
        return teacherPerson;
    }

    public void setTeacherPerson(PersonDTO teacherPerson) {
        this.teacherPerson = teacherPerson;
    }

    public TeacherDTO getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherDTO teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public List<ClassGroupAndSubjectDto> getClassGroupDTOList() {
        return classGroupDTOList;
    }

    public void setClassGroupDTOList(List<ClassGroupAndSubjectDto> classGroupDTOList) {
        this.classGroupDTOList = classGroupDTOList;
    }

    public List<SubjectAmountDto>  getAmountOfGroups() {
        return amountOfGroups;
    }

    public void setAmountOfGroups(List<SubjectAmountDto>  amountOfGroups) {
        this.amountOfGroups = amountOfGroups;
    }

    @Override
    public String toString() {
        return "TeacherDashBoardInfoDTO{" +
            "teacherUserExtend=" + teacherUserExtend +
            ", teacherPerson=" + teacherPerson +
            ", teacherInfo=" + teacherInfo +
            ", employeeDTO=" + employeeDTO +
            ", classGroupDTOList=" + classGroupDTOList +
            ", amountOfGroups=" + amountOfGroups +
            '}';
    }
}
