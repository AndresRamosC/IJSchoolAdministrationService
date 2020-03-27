package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewClassGroupDto implements Serializable {
    private ClassGroupDTO classGroupDTO;
    private List<Long> StudentsIds=new ArrayList<>();
    private List<ClassScheduleDTO> classScheduleDTOS=new ArrayList<>();
    private Long subjectId;
    private Long teacherId;

    public NewClassGroupDto() {
        super();
    }

    public NewClassGroupDto(ClassGroupDTO classGroupDTO, List<Long> studentsIds, List<ClassScheduleDTO> classScheduleDTOS, Long subjectId,Long teacherId) {
        this.classGroupDTO = classGroupDTO;
        StudentsIds = studentsIds;
        this.classScheduleDTOS = classScheduleDTOS;
        this.subjectId = subjectId;
        this.teacherId=teacherId;
    }

    public ClassGroupDTO getClassGroupDTO() {
        return classGroupDTO;
    }

    public void setClassGroupDTO(ClassGroupDTO classGroupDTO) {
        this.classGroupDTO = classGroupDTO;
    }

    public List<Long> getStudentsIds() {
        return StudentsIds;
    }

    public void setStudentsIds(List<Long> studentsIds) {
        StudentsIds = studentsIds;
    }

    public List<ClassScheduleDTO> getClassScheduleDTOS() {
        return classScheduleDTOS;
    }

    public void setClassScheduleDTOS(List<ClassScheduleDTO> classScheduleDTOS) {
        this.classScheduleDTOS = classScheduleDTOS;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "NewClassGroupDto{" +
            "classGroupDTO=" + classGroupDTO +
            ", StudentsIds=" + StudentsIds +
            ", classScheduleDTOS=" + classScheduleDTOS +
            ", subjectId=" + subjectId +
            ", teacherId=" + teacherId +
            '}';
    }
}
