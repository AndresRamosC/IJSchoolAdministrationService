package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.io.Serializable;

public class StudentsInClassRoomDTO implements Serializable {
    private Long studentId;
    private String studentName;
    private Boolean onTime;

    public StudentsInClassRoomDTO(Long studentId, String studentName, Boolean onTime) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.onTime = onTime;
    }

    public StudentsInClassRoomDTO(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Boolean getOnTime() {
        return onTime;
    }

    public void setOnTime(Boolean onTime) {
        this.onTime = onTime;
    }

}
