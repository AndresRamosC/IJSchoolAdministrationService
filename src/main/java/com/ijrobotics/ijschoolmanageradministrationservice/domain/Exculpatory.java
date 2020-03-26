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
 * A Exculpatory.
 */
@Entity
@Table(name = "exculpatory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exculpatory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "leave_date_and_hour")
    private ZonedDateTime leaveDateAndHour;

    @Column(name = "full_day")
    private Boolean fullDay;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "exculpatory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExculpatoryAttachments> exculpatoryAttachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("exculpatories")
    private Guardian guardian;

    @ManyToOne
    @JsonIgnoreProperties("exculpatories")
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

    public Exculpatory creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getLeaveDateAndHour() {
        return leaveDateAndHour;
    }

    public Exculpatory leaveDateAndHour(ZonedDateTime leaveDateAndHour) {
        this.leaveDateAndHour = leaveDateAndHour;
        return this;
    }

    public void setLeaveDateAndHour(ZonedDateTime leaveDateAndHour) {
        this.leaveDateAndHour = leaveDateAndHour;
    }

    public Boolean isFullDay() {
        return fullDay;
    }

    public Exculpatory fullDay(Boolean fullDay) {
        this.fullDay = fullDay;
        return this;
    }

    public void setFullDay(Boolean fullDay) {
        this.fullDay = fullDay;
    }

    public String getDescription() {
        return description;
    }

    public Exculpatory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ExculpatoryAttachments> getExculpatoryAttachments() {
        return exculpatoryAttachments;
    }

    public Exculpatory exculpatoryAttachments(Set<ExculpatoryAttachments> exculpatoryAttachments) {
        this.exculpatoryAttachments = exculpatoryAttachments;
        return this;
    }

    public Exculpatory addExculpatoryAttachments(ExculpatoryAttachments exculpatoryAttachments) {
        this.exculpatoryAttachments.add(exculpatoryAttachments);
        exculpatoryAttachments.setExculpatory(this);
        return this;
    }

    public Exculpatory removeExculpatoryAttachments(ExculpatoryAttachments exculpatoryAttachments) {
        this.exculpatoryAttachments.remove(exculpatoryAttachments);
        exculpatoryAttachments.setExculpatory(null);
        return this;
    }

    public void setExculpatoryAttachments(Set<ExculpatoryAttachments> exculpatoryAttachments) {
        this.exculpatoryAttachments = exculpatoryAttachments;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public Exculpatory guardian(Guardian guardian) {
        this.guardian = guardian;
        return this;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public Student getStudent() {
        return student;
    }

    public Exculpatory student(Student student) {
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
        if (!(o instanceof Exculpatory)) {
            return false;
        }
        return id != null && id.equals(((Exculpatory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exculpatory{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", leaveDateAndHour='" + getLeaveDateAndHour() + "'" +
            ", fullDay='" + isFullDay() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
