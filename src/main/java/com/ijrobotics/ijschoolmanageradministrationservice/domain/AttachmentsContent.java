package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A AttachmentsContent.
 */
@Entity
@Table(name = "attachments_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttachmentsContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    @OneToOne(mappedBy = "attachmentsContent")
    @JsonIgnore
    private Attachments attachments;

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

    public AttachmentsContent creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getData() {
        return data;
    }

    public AttachmentsContent data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public AttachmentsContent dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public AttachmentsContent attachments(Attachments attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentsContent)) {
            return false;
        }
        return id != null && id.equals(((AttachmentsContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttachmentsContent{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            "}";
    }
}
