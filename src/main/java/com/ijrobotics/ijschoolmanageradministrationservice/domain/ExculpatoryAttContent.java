package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ExculpatoryAttContent.
 */
@Entity
@Table(name = "exculpatory_att_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExculpatoryAttContent implements Serializable {

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

    @OneToOne(mappedBy = "exculpatoryAttContent")
    @JsonIgnore
    private ExculpatoryAttachments exculpatoryAttachments;

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

    public ExculpatoryAttContent creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public byte[] getData() {
        return data;
    }

    public ExculpatoryAttContent data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public ExculpatoryAttContent dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public ExculpatoryAttachments getExculpatoryAttachments() {
        return exculpatoryAttachments;
    }

    public ExculpatoryAttContent exculpatoryAttachments(ExculpatoryAttachments exculpatoryAttachments) {
        this.exculpatoryAttachments = exculpatoryAttachments;
        return this;
    }

    public void setExculpatoryAttachments(ExculpatoryAttachments exculpatoryAttachments) {
        this.exculpatoryAttachments = exculpatoryAttachments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExculpatoryAttContent)) {
            return false;
        }
        return id != null && id.equals(((ExculpatoryAttContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExculpatoryAttContent{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            "}";
    }
}
