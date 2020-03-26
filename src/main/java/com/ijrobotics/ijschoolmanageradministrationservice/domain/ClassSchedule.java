package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClassSchedule.
 */
@Entity
@Table(name = "class_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClassSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "start_hour")
    private String startHour;

    @Column(name = "end_hour")
    private String endHour;

    @Column(name = "week_days")
    private Integer weekDays;

    @ManyToMany(mappedBy = "classSchedules")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ClassGroup> classGroups = new HashSet<>();

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

    public ClassSchedule creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public ClassSchedule startHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public ClassSchedule endHour(String endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public Integer getWeekDays() {
        return weekDays;
    }

    public ClassSchedule weekDays(Integer weekDays) {
        this.weekDays = weekDays;
        return this;
    }

    public void setWeekDays(Integer weekDays) {
        this.weekDays = weekDays;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public ClassSchedule classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public ClassSchedule addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.getClassSchedules().add(this);
        return this;
    }

    public ClassSchedule removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.getClassSchedules().remove(this);
        return this;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassSchedule)) {
            return false;
        }
        return id != null && id.equals(((ClassSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClassSchedule{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", weekDays=" + getWeekDays() +
            "}";
    }
}
