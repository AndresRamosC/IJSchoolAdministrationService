package com.ijrobotics.ijschoolmanageradministrationservice.service.dto.IJLogicDTOS;

import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.ClassGroupDTO;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.SubjectDTO;

import java.io.Serializable;

public class ClassGroupAndSubjectDto implements Serializable {
    private Long classGroupId;
    private String groupCode;
    private String startHour;
    private String endHour;
    private String classRoom;
    private Integer weekDays;
    private Long subjectId;
    private String courseName;
    private String courseCode;
    private String department;
    private String colorCode;
    private String iconCode;
    private Long teacherId;

    public ClassGroupAndSubjectDto(ClassGroupDTO classGroupDTO,SubjectDTO subjectDTO) {
        this.classGroupId=classGroupDTO.getId();
        this.groupCode = classGroupDTO.getGroupCode();
        this.startHour = classGroupDTO.getStartHour();
        this.endHour = classGroupDTO.getEndHour();
        this.classRoom = classGroupDTO.getClassRoom();
        this.weekDays = classGroupDTO.getWeekDays();
        this.subjectId = subjectDTO.getId();
        this.courseName = subjectDTO.getCourseName();
        this.courseCode = subjectDTO.getCourseCode();
        this.department = subjectDTO.getDepartment();
        this.colorCode = subjectDTO.getColorCode();
        this.iconCode = subjectDTO.getIconCode();
        this.teacherId = classGroupDTO.getTeacherId();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Integer weekDays) {
        this.weekDays = weekDays;
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

    @Override
    public String toString() {
        return "ClassGroupAndSubjectDto{" +
            "groupCode='" + groupCode + '\'' +
            ", startHour='" + startHour + '\'' +
            ", endHour='" + endHour + '\'' +
            ", classRoom='" + classRoom + '\'' +
            ", weekDays=" + weekDays +
            ", subjectId=" + subjectId +
            ", courseName='" + courseName + '\'' +
            ", courseCode='" + courseCode + '\'' +
            ", department='" + department + '\'' +
            ", colorCode='" + colorCode + '\'' +
            ", iconCode='" + iconCode + '\'' +
            ", teacherId=" + teacherId +
            '}';
    }
}
