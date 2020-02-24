package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "on_time")
    private Boolean onTime;

    @ManyToOne
    @JsonIgnoreProperties("attendances")
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties("attendances")
    private ClassGroup classGroup;

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

    public Attendance creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isOnTime() {
        return onTime;
    }

    public Attendance onTime(Boolean onTime) {
        this.onTime = onTime;
        return this;
    }

    public void setOnTime(Boolean onTime) {
        this.onTime = onTime;
    }

    public Student getStudent() {
        return student;
    }

    public Attendance student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public Attendance classGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
        return this;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", onTime='" + isOnTime() + "'" +
            "}";
    }
}
