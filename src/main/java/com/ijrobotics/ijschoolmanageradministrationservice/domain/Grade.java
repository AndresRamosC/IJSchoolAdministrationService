package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Grade.
 */
@Entity
@Table(name = "grade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "grade")
    private Float grade;

    @OneToOne
    @JoinColumn(unique = true)
    private ClassGroup classGroup;

    @ManyToOne
    @JsonIgnoreProperties("grades")
    private Student student;

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

    public Grade creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getSemester() {
        return semester;
    }

    public Grade semester(Integer semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Float getGrade() {
        return grade;
    }

    public Grade grade(Float grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public Grade classGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
        return this;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    public Student getStudent() {
        return student;
    }

    public Grade student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grade)) {
            return false;
        }
        return id != null && id.equals(((Grade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", semester=" + getSemester() +
            ", grade=" + getGrade() +
            "}";
    }
}
