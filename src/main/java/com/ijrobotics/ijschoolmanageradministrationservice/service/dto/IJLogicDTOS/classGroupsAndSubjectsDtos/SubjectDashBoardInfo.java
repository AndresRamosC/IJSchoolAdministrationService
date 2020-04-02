package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.teacherDtos.TeacherFullInfoDto;

import java.io.Serializable;
import java.util.List;

public class SubjectDashBoardInfo implements Serializable {
    private ClassGroupAndSubjectDto classGroupAndSubjectDto;
    private TeacherFullInfoDto teacherFullInfoDto;

    public SubjectDashBoardInfo(ClassGroupAndSubjectDto classGroupAndSubjectDto, TeacherFullInfoDto teacherFullInfoDto) {
        this.classGroupAndSubjectDto = classGroupAndSubjectDto;
        this.teacherFullInfoDto = teacherFullInfoDto;
    }

    public ClassGroupAndSubjectDto getClassGroupAndSubjectDto() {
        return classGroupAndSubjectDto;
    }

    public void setClassGroupAndSubjectDto(ClassGroupAndSubjectDto classGroupAndSubjectDto) {
        this.classGroupAndSubjectDto = classGroupAndSubjectDto;
    }


    public TeacherFullInfoDto getTeacherFullInfoDto() {
        return teacherFullInfoDto;
    }

    public void setTeacherFullInfoDto(TeacherFullInfoDto teacherFullInfoDto) {
        this.teacherFullInfoDto = teacherFullInfoDto;
    }

    @Override
    public String toString() {
        return "SubjectDashBoardInfo{" +
            "classGroupAndSubjectDto=" + classGroupAndSubjectDto +
            ", teacherFullInfoDto=" + teacherFullInfoDto +
            '}';
    }
}
