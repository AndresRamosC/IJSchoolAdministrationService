package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "department")
    private String department;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "icon_code")
    private String iconCode;

    @OneToMany(mappedBy = "subject")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public Subject creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public Subject courseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Subject courseCode(String courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDepartment() {
        return department;
    }

    public Subject department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getColorCode() {
        return colorCode;
    }

    public Subject colorCode(String colorCode) {
        this.colorCode = colorCode;
        return this;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getIconCode() {
        return iconCode;
    }

    public Subject iconCode(String iconCode) {
        this.iconCode = iconCode;
        return this;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public Subject classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public Subject addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.setSubject(this);
        return this;
    }

    public Subject removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.setSubject(null);
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
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", courseName='" + getCourseName() + "'" +
            ", courseCode='" + getCourseCode() + "'" +
            ", department='" + getDepartment() + "'" +
            ", colorCode='" + getColorCode() + "'" +
            ", iconCode='" + getIconCode() + "'" +
            "}";
    }
}
