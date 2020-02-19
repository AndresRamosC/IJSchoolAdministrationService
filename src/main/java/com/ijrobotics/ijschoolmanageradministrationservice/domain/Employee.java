package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "department")
    private String department;

    @Column(name = "control_number")
    private Long controlNumber;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "employed_since")
    private LocalDate employedSince;

    @Column(name = "employed_until")
    private LocalDate employedUntil;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Employee creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public Employee educationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
        return this;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDepartment() {
        return department;
    }

    public Employee department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getControlNumber() {
        return controlNumber;
    }

    public Employee controlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
        return this;
    }

    public void setControlNumber(Long controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public Employee employmentType(String employmentType) {
        this.employmentType = employmentType;
        return this;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public Employee employedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
        return this;
    }

    public void setEmployedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public Employee employedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
        return this;
    }

    public void setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
    }

    public Person getPerson() {
        return person;
    }

    public Employee person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Employee teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", department='" + getDepartment() + "'" +
            ", controlNumber=" + getControlNumber() +
            ", employmentType='" + getEmploymentType() + "'" +
            ", employedSince='" + getEmployedSince() + "'" +
            ", employedUntil='" + getEmployedUntil() + "'" +
            "}";
    }
}
