package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TeacherDashBoardInfoDTO implements Serializable {
    private UserExtendDTO teacherUserExtend;
    private PersonDTO teacherPerson;
    private TeacherDTO teacherInfo;
    private EmployeeDTO employeeDTO;
    private List<ClassGroupDTO> classGroupDTOList;
    private Map<Long,Long> amountOfGroups;

    public TeacherDashBoardInfoDTO(UserExtendDTO teacherUserExtend, PersonDTO teacherPerson, EmployeeDTO employeeDTO, TeacherDTO teacherInfo, List<ClassGroupDTO> classGroupDTOList,Map<Long,Long> amountOfGroups) {
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

    public List<ClassGroupDTO> getClassGroupDTOList() {
        return classGroupDTOList;
    }

    public void setClassGroupDTOList(List<ClassGroupDTO> classGroupDTOList) {
        this.classGroupDTOList = classGroupDTOList;
    }

    public Map<Long, Long> getAmountOfGroups() {
        return amountOfGroups;
    }

    public void setAmountOfGroups(Map<Long, Long> amountOfGroups) {
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
