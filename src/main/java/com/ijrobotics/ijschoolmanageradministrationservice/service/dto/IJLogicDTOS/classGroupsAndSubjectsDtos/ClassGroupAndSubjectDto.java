package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS.classGroupsAndSubjectsDtos;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassScheduleDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ClassGroupAndSubjectDto implements Serializable {
    private Long classGroupId;
    private String groupCode;
    private String classRoom;
    private Long subjectId;
    private String courseName;
    private String courseCode;
    private String department;
    private String colorCode;
    private String iconCode;
    private Long teacherId;
    private Integer classGroupSize;
    private Set<ClassScheduleDTO> classScheduleDTOS;

    public ClassGroupAndSubjectDto(ClassGroupDTO classGroupDTO,SubjectDTO subjectDTO) {
        this.classGroupId=classGroupDTO.getId();
        this.groupCode = classGroupDTO.getGroupCode();
        this.classScheduleDTOS= classGroupDTO.getClassSchedules();
        this.classRoom = classGroupDTO.getClassRoom();
        this.subjectId = subjectDTO.getId();
        this.courseName = subjectDTO.getCourseName();
        this.courseCode = subjectDTO.getCourseCode();
        this.department = subjectDTO.getDepartment();
        this.colorCode = subjectDTO.getColorCode();
        this.iconCode = subjectDTO.getIconCode();
        this.teacherId = classGroupDTO.getTeacherId();
        this.classGroupSize = classGroupDTO.getSize();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }


    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
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

    public Set<ClassScheduleDTO> getClassScheduleDTOS() {
        return classScheduleDTOS;
    }

    public void setClassScheduleDTOS(Set<ClassScheduleDTO> classScheduleDTOS) {
        this.classScheduleDTOS = classScheduleDTOS;
    }

    public Integer getClassGroupSize() {
        return classGroupSize;
    }

    public void setClassGroupSize(Integer classGroupSize) {
        this.classGroupSize = classGroupSize;
    }

    @Override
    public String toString() {
        return "ClassGroupAndSubjectDto{" +
            "classGroupId=" + classGroupId +
            ", groupCode='" + groupCode + '\'' +
            ", classRoom='" + classRoom + '\'' +
            ", subjectId=" + subjectId +
            ", courseName='" + courseName + '\'' +
            ", courseCode='" + courseCode + '\'' +
            ", department='" + department + '\'' +
            ", colorCode='" + colorCode + '\'' +
            ", iconCode='" + iconCode + '\'' +
            ", teacherId=" + teacherId +
            ", classGroupSize=" + classGroupSize +
            ", classScheduleDTOS=" + classScheduleDTOS +
            '}';
    }
}
