package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ijrobotics.ijschoolmanageradministrationservice.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    private ZonedDateTime creationDate;

    private String educationLevel;

    private String department;

    private Long controlNumber;

    private String employmentType;

    private LocalDate employedSince;

    private LocalDate employedUntil;


    private Long personId;

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

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public void setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", department='" + getDepartment() + "'" +
            ", controlNumber=" + getControlNumber() +
            ", employmentType='" + getEmploymentType() + "'" +
            ", employedSince='" + getEmployedSince() + "'" +
            ", employedUntil='" + getEmployedUntil() + "'" +
            ", personId=" + getPersonId() +
            "}";
    }
}
