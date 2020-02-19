package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassGroup} entity.
 */
public class ClassGroupDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String groupCode;

    private ZonedDateTime startHour;

    private ZonedDateTime endHour;

    private String classRoom;

    private Integer size;


    private Long subjectId;

    private Long teacherId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public ZonedDateTime getStartHour() {
        return startHour;
    }

    public void setStartHour(ZonedDateTime startHour) {
        this.startHour = startHour;
    }

    public ZonedDateTime getEndHour() {
        return endHour;
    }

    public void setEndHour(ZonedDateTime endHour) {
        this.endHour = endHour;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassGroupDTO classGroupDTO = (ClassGroupDTO) o;
        if (classGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassGroupDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", groupCode='" + getGroupCode() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", classRoom='" + getClassRoom() + "'" +
            ", size=" + getSize() +
            ", subjectId=" + getSubjectId() +
            ", teacherId=" + getTeacherId() +
            "}";
    }
}
