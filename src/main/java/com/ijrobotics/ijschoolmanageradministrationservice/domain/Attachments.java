package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Attachments.
 */
@Entity
@Table(name = "attachments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attachments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "title")
    private String title;

    @Column(name = "size")
    private Long size;

    @Column(name = "mime_type")
    private String mimeType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnore
    private AttachmentsContent attachmentsContent;

    @ManyToOne
    @JsonIgnoreProperties("attachments")
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

    public Attachments creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public Attachments title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSize() {
        return size;
    }

    public Attachments size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Attachments mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public AttachmentsContent getAttachmentsContent() {
        return attachmentsContent;
    }

    public Attachments attachmentsContent(AttachmentsContent attachmentsContent) {
        this.attachmentsContent = attachmentsContent;
        return this;
    }

    public void setAttachmentsContent(AttachmentsContent attachmentsContent) {
        this.attachmentsContent = attachmentsContent;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Attachments assignment(Assignment assignment) {
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
        if (!(o instanceof Attachments)) {
            return false;
        }
        return id != null && id.equals(((Attachments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Attachments{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
