package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

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

    @Column(name = "done")
    private Boolean done;

    @Column(name = "grade")
    private Float grade;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private ClassGroup classGroup;

    @ManyToOne
    @JsonIgnoreProperties("assignments")
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

    public Boolean isDone() {
        return done;
    }

    public Assignment done(Boolean done) {
        this.done = done;
        return this;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Float getGrade() {
        return grade;
    }

    public Assignment grade(Float grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Assignment attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Assignment attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
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

    public Student getStudent() {
        return student;
    }

    public Assignment student(Student student) {
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
            ", done='" + isDone() + "'" +
            ", grade=" + getGrade() +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
