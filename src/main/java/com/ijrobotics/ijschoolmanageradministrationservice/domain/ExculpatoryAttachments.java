package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ExculpatoryAttachments.
 */
@Entity
@Table(name = "exculpatory_attachments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExculpatoryAttachments implements Serializable {

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
    private ExculpatoryAttContent exculpatoryAttContent;

    @ManyToOne
    @JsonIgnoreProperties("exculpatoryAttachments")
    private Exculpatory exculpatory;

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

    public ExculpatoryAttachments creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public ExculpatoryAttachments title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSize() {
        return size;
    }

    public ExculpatoryAttachments size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public ExculpatoryAttachments mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ExculpatoryAttContent getExculpatoryAttContent() {
        return exculpatoryAttContent;
    }

    public ExculpatoryAttachments exculpatoryAttContent(ExculpatoryAttContent exculpatoryAttContent) {
        this.exculpatoryAttContent = exculpatoryAttContent;
        return this;
    }

    public void setExculpatoryAttContent(ExculpatoryAttContent exculpatoryAttContent) {
        this.exculpatoryAttContent = exculpatoryAttContent;
    }

    public Exculpatory getExculpatory() {
        return exculpatory;
    }

    public ExculpatoryAttachments exculpatory(Exculpatory exculpatory) {
        this.exculpatory = exculpatory;
        return this;
    }

    public void setExculpatory(Exculpatory exculpatory) {
        this.exculpatory = exculpatory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExculpatoryAttachments)) {
            return false;
        }
        return id != null && id.equals(((ExculpatoryAttachments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExculpatoryAttachments{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", size=" + getSize() +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }
}
