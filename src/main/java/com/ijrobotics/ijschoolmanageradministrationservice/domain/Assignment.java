package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Assignment.
 */
@Entity
@Table(name = "assignment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    @OneToMany(mappedBy = "assignment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AssignmentAssigned> assignmentAssigneds = new HashSet<>();

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attachments> attachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("assignments")
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

    public Assignment creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public Assignment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Assignment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Assignment dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Set<AssignmentAssigned> getAssignmentAssigneds() {
        return assignmentAssigneds;
    }

    public Assignment assignmentAssigneds(Set<AssignmentAssigned> assignmentAssigneds) {
        this.assignmentAssigneds = assignmentAssigneds;
        return this;
    }

    public Assignment addAssignmentAssigned(AssignmentAssigned assignmentAssigned) {
        this.assignmentAssigneds.add(assignmentAssigned);
        assignmentAssigned.setAssignment(this);
        return this;
    }

    public Assignment removeAssignmentAssigned(AssignmentAssigned assignmentAssigned) {
        this.assignmentAssigneds.remove(assignmentAssigned);
        assignmentAssigned.setAssignment(null);
        return this;
    }

    public void setAssignmentAssigneds(Set<AssignmentAssigned> assignmentAssigneds) {
        this.assignmentAssigneds = assignmentAssigneds;
    }

    public Set<Attachments> getAttachments() {
        return attachments;
    }

    public Assignment attachments(Set<Attachments> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Assignment addAttachments(Attachments attachments) {
        this.attachments.add(attachments);
        attachments.setAssignment(this);
        return this;
    }

    public Assignment removeAttachments(Attachments attachments) {
        this.attachments.remove(attachments);
        attachments.setAssignment(null);
        return this;
    }

    public void setAttachments(Set<Attachments> attachments) {
        this.attachments = attachments;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public Assignment classGroup(ClassGroup classGroup) {
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
        if (!(o instanceof Assignment)) {
            return false;
        }
        return id != null && id.equals(((Assignment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }
}
