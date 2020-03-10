package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A AssignmentAssigned.
 */
@Entity
@Table(name = "assignment_assigned")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AssignmentAssigned implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "grade")
    private Float grade;

    @ManyToOne
    @JsonIgnoreProperties("assignmentAssigneds")
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties("assignmentAssigneds")
    private Assignment assignment;

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

    public AssignmentAssigned creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isDone() {
        return done;
    }

    public AssignmentAssigned done(Boolean done) {
        this.done = done;
        return this;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Float getGrade() {
        return grade;
    }

    public AssignmentAssigned grade(Float grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public AssignmentAssigned student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public AssignmentAssigned assignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentAssigned)) {
            return false;
        }
        return id != null && id.equals(((AssignmentAssigned) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssignmentAssigned{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", done='" + isDone() + "'" +
            ", grade=" + getGrade() +
            "}";
    }
}
