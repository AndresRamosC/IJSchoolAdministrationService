package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.ClassSchedule} entity.
 */
public class ClassScheduleDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String startHour;

    private String endHour;

    private Integer weekDays;


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

    public Integer getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Integer weekDays) {
        this.weekDays = weekDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassScheduleDTO classScheduleDTO = (ClassScheduleDTO) o;
        if (classScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassScheduleDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", weekDays=" + getWeekDays() +
            "}";
    }
}
